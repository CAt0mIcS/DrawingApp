package com.example.testdrawingapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Page
{
    public enum Template
    {
        Infinite,
        A4
    }

    private Bitmap mBitmap;
    private Canvas mCanvas;
    public Template template;

    /*
    * Specifies the offset to the left by which the bitmap should be moved
    * */
    public float offsetX = 0;

    /*
     * Specifies the offset to the bottom by which the bitmap should be moved
     * */
    public float offsetY = 0;

    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public Page(Template template)
    {
        this.template = template;

        int width = 0, height = 0;
        switch(this.template)
        {
            case Infinite:
                break;
            case A4:
                width = 3508;
                height = 2480;
                break;
        }

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    public void drawBackground()
    {
        mCanvas.drawColor(Settings.BackgroundColor);
    }

    public void drawPath(Path path, Paint paint)
    {
        mCanvas.drawPath(path, paint);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(mBitmap, offsetX, offsetY, mBitmapPaint);
    }
}
