package com.example.zulkar_game;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;

import androidx.annotation.NonNull;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    final SurfaceHolder surfaceHolder;
    DrawingThread drawingThread;
    VelocityTracker velocityTracker;

    public GameView(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);//surface holder er sathe callback ta k add kore dilam.

        velocityTracker = VelocityTracker.obtain();
        drawingThread = new DrawingThread(this,context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);//surface holder er sathe callback ta k add kore dilam.

        velocityTracker = VelocityTracker.obtain();
        drawingThread = new DrawingThread(this,context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);//surface holder er sathe callback ta k add kore dilam.

        velocityTracker = VelocityTracker.obtain();
        drawingThread = new DrawingThread(this,context);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        try {
            drawingThread.start();
        } catch (Exception e) {
            restartThread();
        }
    }

    private void restartThread() {
        drawingThread.stopThread();
        drawingThread = null;
        drawingThread = new DrawingThread(this,context);
        drawingThread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawingThread.stopThread();
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(drawingThread.pauseFlag)
        {
            return true;
        }
        Point touchPoint = new Point((int) event.getX(), (int) event.getY());
        Random random = new Random();
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                drawingThread.touchFlag = true;
                drawingThread.allRobots.add(new Robot(drawingThread.allPossibleRobots.get(random.nextInt(5)),touchPoint));

                break;
            case MotionEvent.ACTION_UP:
                drawingThread.touchFlag = false;
                velocityTracker.computeCurrentVelocity(40);
                drawingThread.allRobots.get(drawingThread.allRobots.size()-1).setVelocity(velocityTracker);
                velocityTracker.clear();
                break;
            case MotionEvent.ACTION_MOVE:
//                Point touch = new Point((int) event.getX(), (int) event.getY());
                drawingThread.allRobots.get(drawingThread.allRobots.size()-1).setCenter(touchPoint);
                velocityTracker.addMovement(event);
//                velocityTracker.computeCurrentVelocity(40);

                break;
        }

        return true;
    }
}
