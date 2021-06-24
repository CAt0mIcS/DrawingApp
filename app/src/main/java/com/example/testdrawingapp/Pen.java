package com.example.testdrawingapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;

public class Pen implements IDrawableListener
{
    public static int BRUSH_SIZE = 10;
    public int color;
    public static final float TOUCH_TOLERANCE = 4;
    private Path mPath;
    public ArrayList<FingerPath> paths = new ArrayList<>();
    private float mX, mY;
    public Paint paint;

    public Pen(int color)
    {
        this.color = color;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(this.color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(null);
        paint.setAlpha(0xff);
    }

    @Override
    public void touchStart(DrawView view, float x, float y)
    {
        mPath = new Path();
        FingerPath fp = new FingerPath(color, BRUSH_SIZE, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

        mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
    }

    @Override
    public void touchMove(DrawView view, float x, float y)
    {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
        {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    @Override
    public void touchEnd(DrawView view, float x, float y)
    {
        mPath.lineTo(mX, mY);
    }
}
