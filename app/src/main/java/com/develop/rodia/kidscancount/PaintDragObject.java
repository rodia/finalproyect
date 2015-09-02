package com.develop.rodia.kidscancount;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.develop.rodia.kidscancount.data.KCCDbHelper;
import com.develop.rodia.kidscancount.data.ResultContract;
import com.develop.rodia.kidscancount.model.StageModel;

import java.util.Random;

/**
 * This class manage the process to game.
 *
 * @version 0.1
 */
public class PaintDragObject extends View implements SensorEventListener {
    private static final int DEFAULT_COUNT_VALUES = 5;
    private static final String LOG_CLASS = PaintDragObject.class.getSimpleName();
    private static final int DEFAULT_WIDTH_DEVICE = 600;
    private static final int DEFAULT_HEIGHT_DEVICE = 950;
    private int totalForCount;
    private DragObject[] dragObjects;
    private int objectID = 0;
    private MotionEvent event;

    private SensorManager mSensorManager = null;

    private static int currentStage;

    /**
     * @param context
     */
    public PaintDragObject(Context context) {
        super(context);
        currentStage = getCurrentStage();
        totalForCount = getTotalForStage(currentStage);
        dragObjects = new DragObject[totalForCount];
        setFocusable(true);
        Log.d(LOG_CLASS, "Start Paint with " + totalForCount);
        for (int i = 0; i < totalForCount; i++) {
            Point point = getDefinedPoint(currentStage, i);
            dragObjects[i] = new DragObject(context, drawableElement(currentStage), point);
        }

        mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_NORMAL);

        setBackgroundForStage(currentStage);
    }

    /**
     * @todo This function show the current background for current stage.
     * @param stage
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setBackgroundForStage(int stage) {
        FrameLayout layout = (FrameLayout)findViewById(R.id.container);
        if (null == layout) {
            return;
        }
        layout.setBackgroundResource(R.drawable.bgcielo);
    }

    /**
     * Get the total elements to count for stage. The current element is the total element
     * tha are type 0. The type 0 represent the img count resource.
     *
     * If no there is any value the return is a default value, defined in values dimens.xml.
     * @param id The id for stage.
     * @return int
     */
    private int getTotalForStage(int id) {
        SQLiteDatabase db = new KCCDbHelper(getContext()).getWritableDatabase();
        String[] args = new String[] {id + "", "0"};

        String sql_query = "SELECT count(*) AS total" +
                " FROM " + ResultContract.StageEntry.TABLE_NAME +
                " INNER JOIN " + ResultContract.ResourceEntry.TABLE_NAME +
                " ON (" + ResultContract.ResourceEntry.TABLE_NAME + "." +
                ResultContract.ResourceEntry.COLUMN_STAGE_ID + "=" +
                ResultContract.StageEntry.TABLE_NAME + "." + ResultContract.StageEntry._ID + ")" +
                " WHERE " + ResultContract.ResourceEntry.TABLE_NAME + "." +
                ResultContract.ResourceEntry.COLUMN_STAGE_ID + " = ?" +
                " AND " + ResultContract.ResourceEntry.TABLE_NAME + "." +
                ResultContract.ResourceEntry.COLUMN_TYPE + " = ?";
        Cursor c = db.rawQuery(sql_query, args);
        if (c.moveToFirst()) {
            do {
                String temp = c.getString(0);
                break;
            } while(c.moveToNext());
            if (0 == currentStage) {
                currentStage = DEFAULT_COUNT_VALUES;
            }
        } else {
            currentStage = DEFAULT_COUNT_VALUES;
        }
        return currentStage;
    }

    /**
     * Get one value for
     * @return
     */
    protected Point getDefinedPoint(int currentStage, int element) {
        Random rand = new Random();
        Point point1 = new Point();

        int width = DEFAULT_WIDTH_DEVICE;
        int height = DEFAULT_HEIGHT_DEVICE;

        if (width <= 0) {
            width = 0;
        }
        if (height <= 0) {
            height = 0;
        }
        point1.x = rand.nextInt(width);
        point1.y = rand.nextInt(height);

        return point1;
    }

    /**
     * Draw the each object
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundForDraw(canvas, currentStage);
        for (DragObject obj : dragObjects) {
            canvas.drawBitmap(obj.getBitmap(), obj.getX(), obj.getY(), null);
        }
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        int event_action = event.getAction();
        int X = (int)event.getX();
        int Y = (int)event.getY();
        switch (event_action ) {
            case MotionEvent.ACTION_DOWN:
                objectID = 0;
                for (DragObject obj : dragObjects) {
                    int centerX = obj.getX() + 50;
                    int centerY = obj.getY() + 50;
                    double radCircle = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));
                    if (radCircle < 50){
                        objectID = obj.getID();
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (objectID > 0) {
                    dragObjects[objectID-1].setX(X - 50);
                    dragObjects[objectID-1].setY(Y - 50);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    /**
     * This function get the current stage to drawing.
     * @return
     */
    public int getCurrentStage() {
        if (0 == currentStage) {
            StageModel model = new StageModel();
            currentStage = (int)model.getCurrent(getContext());
        }
        return currentStage;
    }

    /**
     * Get the element to show in the panel for count.
     * @param stage The resource to stage.
     * @return Resource element.
     */
    private int drawableElement(int stage) {

        return R.drawable.cat;
    }

    /**
     * Set Background to current stage
     * @todo This method need find the current bg for defined current stage.
     * @param canvas
     * @param stage
     */
    protected void setBackgroundForDraw(Canvas canvas, int stage) {
        canvas.drawRGB(255, 255, 0);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Bitmap bg =  BitmapFactory.decodeResource(getResources(), R.drawable.bgcielo);
        canvas.drawBitmap(bg, 0, 0, null);
    }

    /**
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(LOG_CLASS, "Change " + event.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(LOG_CLASS, "Ocurrance " + event.toString());
    }
}
