package com.example.zulkar_game;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.VelocityTracker;

public class Robot {

    float centerX,centerY;
    float velocityX,velocityY;
    int height,width;
    Bitmap robotBitmap;
    Paint robotPaint;

    public Robot(Bitmap bitmap){
        robotBitmap = bitmap;
        centerX = centerY = 0;
        height = robotBitmap.getHeight();
        width = robotBitmap.getWidth();
        robotPaint = new Paint();
        velocityX = velocityY = 0;
    }

    public Robot(Bitmap bitmap,int cX, int cY){
        this(bitmap);
        centerX = cX;
        centerY = cY;

    }
    public void setCenter(Point centerPoint)
    {
        centerX = centerPoint.x;
        centerY = centerPoint.y;
    }
    public void setVelocity(VelocityTracker velocityTracker)
    {
        velocityX = velocityTracker.getXVelocity();
        velocityY = velocityTracker.getYVelocity();
    }

    public Robot(Bitmap bitmap , Point center){
        this(bitmap, center.x, center.y);
    }
}
