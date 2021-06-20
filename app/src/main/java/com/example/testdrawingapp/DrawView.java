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
    public static int BRUSH_SIZE = 10;
    public static final int DEFAULT_COLOR = Color.BLACK;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    public static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public DrawView(Context context)
    {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }

    public void init(DisplayMetrics metrics)
    {
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
        for(FingerPath fp : paths)
        {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mPaint.setMaskFilter(null);
            mCanvas.drawPath(fp.path, mPaint);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y)
    {
        mPath = new Path();
        FingerPath fp = new FingerPath(DEFAULT_COLOR, BRUSH_SIZE, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y)
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

    private void touchEnd()
    {
        mPath.lineTo(mX, mY);
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
                    touchEnd();
                    invalidate();
                    return true;
        }
        return false;
    }
}
