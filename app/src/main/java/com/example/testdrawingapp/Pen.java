package com.example.testdrawingapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

public class Pen implements IDrawableListener
{
    public static int BRUSH_SIZE = 10;
    public static final int DEFAULT_COLOR = Color.BLACK;
    public static final float TOUCH_TOLERANCE = 4;
    private Path mPath;
    public ArrayList<FingerPath> paths = new ArrayList<>();
    private float mX, mY;
    public Paint paint;

    public Pen()
    {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(DEFAULT_COLOR);
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
        FingerPath fp = new FingerPath(DEFAULT_COLOR, BRUSH_SIZE, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
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
