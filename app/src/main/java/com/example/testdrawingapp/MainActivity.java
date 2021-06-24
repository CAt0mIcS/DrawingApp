package com.example.testdrawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity
{
    private DrawView mDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawView = findViewById(R.id.drawView);
        DisplayMetrics metrics = new DisplayMetrics();
        mDrawView.init(new Pen(Color.BLACK));
        DrawView.TOOL_TYPES.add(MotionEvent.TOOL_TYPE_STYLUS);
        DrawView.TOOL_TYPES.add(MotionEvent.TOOL_TYPE_MOUSE);
        DrawView.TOOL_TYPES.add(MotionEvent.TOOL_TYPE_ERASER);
        DrawView.TOOL_TYPES.add(MotionEvent.TOOL_TYPE_FINGER);

    }
}