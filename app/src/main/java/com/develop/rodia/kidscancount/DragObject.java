package com.develop.rodia.kidscancount;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * <p></p>This class is an generic object for object move
 * <p/>
 * Created by Rodia on 01/04/2015.
 */
public class DragObject {
    private Bitmap img;
    private int coordX = 0;
    private int coordY = 0;
    private int id;
    private static int count = 1;
    private boolean right = true;
    private boolean bellow = true;


    /**
     * Construct to object drag.
     *
     * @param context
     * @param drawable
     */
    public DragObject(Context context, int drawable) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        img = BitmapFactory.decodeResource(context.getResources(), drawable);
        id = count;
        count++;
    }

    /**
     * Construct to object drag.
     *
     * @param context
     * @param drawable
     * @param point
     */
    public DragObject(Context context, int drawable, Point point) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        img = BitmapFactory.decodeResource(context.getResources(), drawable);
        id = count;
        count++;
        coordX = point.x;
        coordY = point.y;
    }

    public static int getCount() {
        return count;
    }

    public void setX(int newValue) {
        coordX = newValue;
    }

    public int getX() {
        return coordX;
    }

    public void setY(int newValue) {
        coordY = newValue;
    }

    public int getY() {
        return coordY;
    }

    public int getID() {
        return id;
    }

    public Bitmap getBitmap() {
        return img;
    }

    /**
     * Check the bound and reset if the object get the limit
     *
     * @param goX
     * @param goY
     */
    public void moveObject(int goX, int goY) {
        if (coordX > 270) {
            right = false;
        }
        if (coordX < 0) {
            right = true;
        }
        if (coordY > 400) {
            bellow = false;
        }
        if (coordY < 0) {
            bellow = true;
        }
        if (right) {
            coordX += goX;
        } else {
            coordX -= goX;
        }
        if (bellow) {
            coordY += goY;
        } else {
            coordY -= goY;
        }
    }
}
