package com.example.zulkar_game;

import static android.view.MotionEvent.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton btnStart;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        
        btnStart = findViewById(R.id.startButton);
       btnStart.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               switch(motionEvent.getAction()) {
                   case ACTION_UP:
                       Intent intent = new Intent(MainActivity.this,GameActivity.class);
                       startActivity(intent);
                       btnStart.getBackground().setAlpha(255);
                       break;
                   case ACTION_DOWN:
                       btnStart.getBackground().setAlpha(100);
                       break;
                   default:
                       Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
                       break;
               }
               return false;
           }
       });
       
    }
}