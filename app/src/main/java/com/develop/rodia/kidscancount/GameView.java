package com.develop.rodia.kidscancount;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.develop.rodia.kidscancount.model.Counter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contain
 */
public class GameView extends SurfaceView {
    private GameLoopThread gameLoopThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private long lastClick;
    private Bitmap bmpBlood;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {}
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createSprites();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
    }

    private void createSprites() {
        sprites.add(createSprite(R.drawable.catrun));
        sprites.add(createSprite(R.drawable.catrun2));
        sprites.add(createSprite(R.drawable.catrun3));
        sprites.add(createSprite(R.drawable.catrun4));
        sprites.add(createSprite(R.drawable.catrun5));
        sprites.add(createSprite(R.drawable.catrun6));
        sprites.add(createSprite(R.drawable.catrun7));
        sprites.add(createSprite(R.drawable.catrun8));
        sprites.add(createSprite(R.drawable.catrun9));
        sprites.add(createSprite(R.drawable.catrun10));
    }

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this, bmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null) {
            Counter.count = 0;
            return;
        }
        Bitmap bg =  BitmapFactory.decodeResource(getResources(), R.drawable.madejas);
        canvas.drawBitmap(bg, 0, 0, null);

        for (int i = temps.size() - 1; i >= 0; i--) {
            temps.get(i).onDraw(canvas);
        }
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 300) {
            lastClick = System.currentTimeMillis();
            float x = event.getX();
            float y = event.getY();
            synchronized (getHolder()) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    if (sprite.isCollition(x, y)) {
                        sprites.remove(sprite);
                        TempSprite temp = new TempSprite(temps, this, x, y, bmpBlood);
                        temp.setCount(++Counter.count);
                        temps.add(temp);
                        break;
                    }
                }
            }
        }
        return true;
    }
}