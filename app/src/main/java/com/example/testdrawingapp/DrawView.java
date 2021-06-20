package com.example.testdrawingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawView extends View
{
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private Pen mPen;

    public DrawView(Context context)
    {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void init(DisplayMetrics metrics, Pen pen)
    {
        mPen = pen;

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        mCanvas.drawColor(DEFAULT_BG_COLOR);
        for(FingerPath fp : mPen.paths)
        {
            mPen.paint.setColor(fp.color);
            mPen.paint.setStrokeWidth(fp.strokeWidth);
            mPen.paint.setMaskFilter(null);
            mCanvas.drawPath(fp.path, mPen.paint);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y)
    {
        mPen.touchStart(this, x, y);
    }

    private void touchMove(float x, float y)
    {
        mPen.touchMove(this, x, y);
    }

    private void touchEnd(float x, float y)
    {
        mPen.touchEnd(this, x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        int toolType = event.getToolType(0);
        switch(toolType)
        {
            case MotionEvent.TOOL_TYPE_STYLUS:
                Log.d("DrawView", "onTouchEven TOOL_TYPE_STYLUS");
                break;
            case MotionEvent.TOOL_TYPE_MOUSE:
                Log.d("DrawView", "onTouchEvent TOOL_TYPE_MOUSE");
                break;
            case MotionEvent.TOOL_TYPE_ERASER:
                Log.d("DrawView", "onTouchEvent TOOL_TYPE_ERASER");
                break;
            case MotionEvent.TOOL_TYPE_FINGER:
                Log.d("DrawView", "onTouchEvent TOOL_TYPE_FINGER");
                break;
            default:
                Log.d("DrawView", "onTouchEvent TOOL_TYPE_UNKNOWN");
                break;
        }

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    touchStart(x, y);
                    invalidate();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    touchMove(x, y);
                    invalidate();
                    return true;
                case MotionEvent.ACTION_UP:
                    touchEnd(x, y);
                    invalidate();
                    return true;
        }
        return false;
    }
}
