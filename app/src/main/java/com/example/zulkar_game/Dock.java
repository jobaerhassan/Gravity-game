package com.example.zulkar_game;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Dock {

    Bitmap dockBitmap;
    int dockWidth,dockHeight;
    int leftBottomMostPoint, rightBottomMostPoint;

    Point topleftPoint=new Point(0, 0),bottomCenterPoint;
    DrawingThread drawingThread;


    boolean movingLeftFlag=false;
    boolean movingRightFlag=false;



    public Dock(DrawingThread drawingThread, int bitmapId) {
        this.drawingThread=drawingThread;
        Bitmap tempBitmap=BitmapFactory.decodeResource(drawingThread.context.getResources(), bitmapId);
        tempBitmap=Bitmap.createScaledBitmap(tempBitmap, drawingThread.displayX, drawingThread.displayX*tempBitmap.getHeight()/tempBitmap.getWidth(), true);
//         tempBitmap = Bitmap.createScaledBitmap(tempBitmap,100,100,true);
        dockBitmap=tempBitmap;
        dockHeight=dockBitmap.getHeight();
        dockWidth=dockBitmap.getWidth();



        bottomCenterPoint=new Point(drawingThread.displayX/2, drawingThread.displayY);
        topleftPoint.y=bottomCenterPoint.y-dockHeight;

        updateInfo();


    }

    private void updateInfo() {
        leftBottomMostPoint=bottomCenterPoint.x-dockWidth/2;
        rightBottomMostPoint=bottomCenterPoint.x+dockWidth/2;

        topleftPoint.x=leftBottomMostPoint;
    }


    public void moveDockToLeft() {
        bottomCenterPoint.y-=4;
        updateInfo();
    }

    public void moveDockToRight() {
        bottomCenterPoint.y+=4;
        updateInfo();
    }

    public void startMovingLeft() {
        Thread thread=new Thread(){
            @Override
            public void run() {
                movingLeftFlag=true;

                while (movingLeftFlag) {
                    moveDockToLeft();
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public void stopMovingLeft() {
        movingLeftFlag=false;
    }

    public void startMovingRight() {
        Thread thread=new Thread(){
            @Override
            public void run() {
                movingRightFlag=true;

                while (movingRightFlag) {
                    moveDockToRight();
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public void stopMovingRight() {
        movingRightFlag=false;
    }

}