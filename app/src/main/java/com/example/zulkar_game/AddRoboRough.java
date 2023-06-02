package com.example.zulkar_game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.WindowManager;

public class AddRoboRough {
    int leftmostpoint;
    int rightmostpoint;
    Bitmap bitmapRobo;
    DrawingThread drawingThread;
    int displayXX,displayYY;
//    Point pos = new Point(0,0);







    public AddRoboRough(DrawingThread drawingThread, int resource){
        this.drawingThread = drawingThread;
        Bitmap bitmap = BitmapFactory.decodeResource(drawingThread.context.getResources(),resource);
        bitmap=Bitmap.createScaledBitmap(bitmap,1,500,true);
        bitmapRobo = bitmap;


    }
}
