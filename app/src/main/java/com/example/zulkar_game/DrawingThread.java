package com.example.zulkar_game;

//import static com.example.zulkar_game.GameActivity.gX;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class DrawingThread extends Thread {

    private Canvas canvas;
    GameView gameView;
    Context context;
    boolean pauseFlag = false;
    boolean threadFlag = false;
    AddRoboRough addRoboRough;
    Dock dock;
    Bitmap backgroundBitmap;
    int displayX, displayY;
    ArrayList<Robot> allRobots;
    ArrayList<Bitmap> allPossibleRobots;
    boolean touchFlag = false;
    AnimationThread animationThread;

    public DrawingThread(GameView gameView, Context context) {
        this.gameView = gameView;
        this.context = context;

        initializeAll();
    }

    private void initializeAll() {
        //display er size ta jana dorkar::
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point displayDimension = new Point(0, 0);
        defaultDisplay.getSize(displayDimension);
        displayX = displayDimension.x;
        displayY = displayDimension.y;
        addRoboRough = new AddRoboRough(this, R.drawable.robot1);
        dock = new Dock(this, R.drawable.dock);

        backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, displayX, displayY, true);

        initializeAllPossibleRobots();

    }

    private void initializeAllPossibleRobots() {
        allRobots = new ArrayList<Robot>();
        allPossibleRobots = new ArrayList<Bitmap>();
        allPossibleRobots.add(giveResizedRobotBitmap(R.drawable.robot1));
        allPossibleRobots.add(giveResizedRobotBitmap(R.drawable.robot2));
        allPossibleRobots.add(giveResizedRobotBitmap(R.drawable.robot3));
        allPossibleRobots.add(giveResizedRobotBitmap(R.drawable.robot4));
        allPossibleRobots.add(giveResizedRobotBitmap(R.drawable.robot5));

    }

    private Bitmap giveResizedRobotBitmap(int resourseID) {
        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), resourseID);
        tempBitmap = Bitmap.createScaledBitmap(tempBitmap, displayX / 5, (tempBitmap.getHeight() / tempBitmap.getWidth()) * (displayX / 5), true);
        return tempBitmap;
    }

    @Override
    public void run() {
        threadFlag = true;
        animationThread = new AnimationThread(this);
        animationThread.start();
        while (threadFlag) {
            canvas = gameView.surfaceHolder.lockCanvas();//lock korlam
            try {
                synchronized (gameView.surfaceHolder)//sobkisu organized/sychronized thakbe.
                {
                    updateDispaly();
//                    drawAddroboRough();
                    drawDock();

                }
            } catch (Exception e) {

                throw new RuntimeException(e);

            } finally {
                //display update howar por finally te asbe.
                if (canvas != null) {
                    gameView.surfaceHolder.unlockCanvasAndPost(canvas);
                    //updateDisplay method call howar por finally te eshe check korbe j canvas khali kina.
                    //khali na hole unlock kore dibe and post kore dibe..bar bar eta korte thakbe..
                }
            }
            try {
                Thread.sleep(2); //1000milis/60fps == 17 millis
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        animationThread.stopThread();
    }

    private void updateDispaly() {


        canvas.drawBitmap(backgroundBitmap, 0, 0, null);

        for (int i = 0; i < allRobots.size(); i++) {
            Robot tempRobot = allRobots.get(i);
            canvas.drawBitmap(tempRobot.robotBitmap, tempRobot.centerX - (tempRobot.width / 2), tempRobot.centerY - (tempRobot.height / 2), tempRobot.robotPaint);

        }
        if (pauseFlag) {
            pauseStateDraw();
        }
//        drawSensorData();
    }

    private void pauseStateDraw() {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        paint.setAlpha(170);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawARGB(150, 0, 0, 0);//0 == full transparent and 255 == full oshoccho
        canvas.drawText("PAUSED", displayX / 2, displayY / 2, paint);
    }

//    private void drawSensorData() {
//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        paint.setTextSize(100);
//        canvas.drawText("X : "+GameActivity.getgX()+"\n",0,displayY/2,paint);
//        canvas.drawText("Y : "+GameActivity.getgY(),0,displayY/2+100,paint);
//    }

    public void stopThread() {
        threadFlag = false;
    }

    public void drawDock() {
        canvas.drawBitmap(dock.dockBitmap, dock.topleftPoint.x, dock.bottomCenterPoint.x, null);
//        canvas.drawBitmap(addRoboRough.bitmapRobo,100,100,null);
//    }
//    public void drawAddroboRough()
//    {
//        canvas.drawBitmap(addRoboRough.bitmapRobo,0,200,null);
//    }

    }
}
