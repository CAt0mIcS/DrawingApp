package com.example.testdrawingapp;


public interface IDrawableListener
{
    void touchStart(DrawView view, float x, float y);
    void touchMove(DrawView view, float x, float y);
    void touchEnd(DrawView view, float x, float y);
}
