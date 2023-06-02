package com.example.zulkar_game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {
    SensorManager sensorManager;
    SensorEventListener sensorEventListener;
    Sensor accelerometer;
    private static float gX, gY;
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getSupportActionBar().hide();
        initializeSensors();
//        GameView gameView = new GameView(this);
//        setContentView(R.layout.activity_game); //eta xml layout file er view.bt amra to gameView banaisi extra..seta use korbo.
        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.myGameView);

        initializeButtons();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeButtons() {
        Button moveLeftButton = findViewById(R.id.buttonLeft);
        Button moveRightButton = findViewById(R.id.buttonRight);
        moveLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        gameView.drawingThread.dock.startMovingLeft();
                        moveLeftButton.getBackground().setAlpha(100);
                        break;
                    case MotionEvent.ACTION_UP:
                        gameView.drawingThread.dock.stopMovingLeft();
                        moveLeftButton.getBackground().setAlpha(255);
                        break;

                    default:
                        break;
                }

                return false;
            }
        });

        moveRightButton.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    gameView.drawingThread.dock.startMovingRight();
                    moveRightButton.getBackground().setAlpha(100);
                    break;
                case MotionEvent.ACTION_UP:
                    gameView.drawingThread.dock.stopMovingRight();
                    moveRightButton.getBackground().setAlpha(255);
                    break;

                default:
                    break;
            }

            return false;
//                return false;
        });
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    gX = -1 * sensorEvent.values[0];
                    gY = sensorEvent.values[1];
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        startUsingSensor();
    }

    private void startUsingSensor() {
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stopUsingSensor() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static float getgX() {
        return gX;
    }

    public static float getgY() {
        return gY;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void pauseGame(View view) {
        if (!gameView.drawingThread.pauseFlag) {
            gameView.drawingThread.animationThread.stopThread();
            gameView.drawingThread.pauseFlag = true;
            //button ta change o korte hobe
            view.setBackgroundResource(R.drawable.unlock);
        } else {
            view.setBackgroundResource(R.drawable.lock);
            gameView.drawingThread.animationThread = new AnimationThread(gameView.drawingThread);
            gameView.drawingThread.animationThread.start();
            gameView.drawingThread.pauseFlag = false;
        }

    }

    public void restartGame(View view) {
        gameView.drawingThread.allRobots.clear();
    }

    public void stopGame(View view) {
        this.finish();
    }
}