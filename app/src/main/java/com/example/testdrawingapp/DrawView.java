package com.example.testdrawingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;

public class DrawView extends View
{
    public static final ArrayList<Integer> TOOL_TYPES = new ArrayList<>();
    private Page mPage;
    private Pen mPen;

    public DrawView(Context context)
    {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void init(Pen pen)
    {
        mPen = pen;
        mPage = new Page(Page.Template.A4);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        mPage.drawBackground();
        for(FingerPath fp : mPen.paths)
        {
            mPen.paint.setColor(fp.color);
            mPen.paint.setStrokeWidth(fp.strokeWidth);
            mPen.paint.setMaskFilter(null);
            mPage.drawPath(fp.path, mPen.paint);
        }

        mPage.draw(canvas);
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

        if(TOOL_TYPES.contains(event.getToolType(0)))
        {
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
        }
        return false;
    }
}
