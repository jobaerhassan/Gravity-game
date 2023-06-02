package com.example.zulkar_game;

import android.view.VelocityTracker;

public class AnimationThread extends Thread{
    private boolean flag = false;
    DrawingThread drawingThread;
    float gravityX,gravityY;
    float timeConstant = 0.5f;
    float retardationRatio = -0.7f;
    int width,height;
    int left,right,top,bottom;
//    VelocityTracker velocityTracker ;

    public AnimationThread(DrawingThread drawingThread) {
        this.drawingThread = drawingThread;
        updateDimentions();
    }

    private void updateDimentions() {
        width = drawingThread.allPossibleRobots.get(0).getWidth();
        height = drawingThread.allPossibleRobots.get(0).getHeight();
        left = width/2;
        top = height/2;
        right = drawingThread.displayX - (width/2);
        bottom= drawingThread.displayY - (height/2);

    }

    @Override
    public void run() {
        flag = true;
        while(flag)
        {
            updateAllPosition();

            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateAllPosition() {
        gravityX = GameActivity.getgX();
        gravityY = GameActivity.getgY();
        if(drawingThread.touchFlag) {
            for(int i = 0; i < drawingThread.allRobots.size()-1; i++)
            {
                updateRobotsPosition(drawingThread.allRobots.get(i));
            }
        }
        else{
            for(int i = 0; i < drawingThread.allRobots.size(); i++)
            {
                updateRobotsPosition(drawingThread.allRobots.get(i));
            }
        }


    }

    private void updateRobotsPosition(Robot robot) {
        robot.centerX += (robot.velocityX*timeConstant+0.5*gravityX*timeConstant*timeConstant);
        robot.velocityX += gravityX*timeConstant;



        robot.centerY += (robot.velocityY*timeConstant+0.5*gravityY*timeConstant*timeConstant);
        robot.velocityY += gravityY*timeConstant;


        constrainPosition(robot);
    }

    private void constrainPosition(Robot robot) {
        //for x axis
        if(robot.centerX<left){
            robot.centerX = left;
            robot.velocityX *=retardationRatio;
        }
        else if(robot.centerX>right)
        {
            robot.centerX = right;
            robot.velocityX*=retardationRatio;
        }


        //for y axis
//        if(robot.centerY<top)//top er upore means 0 er cheye choto.negetive e value;
//        {
//            robot.centerY = top;
//            robot.velocityY *= retardationRatio;
//        }
//        else

        if(robot.centerY>bottom)
        {
            robot.centerY = bottom;
            robot.velocityY *= retardationRatio;
        }
    }


    public void stopThread()
    {
        flag = false;
    }
}
