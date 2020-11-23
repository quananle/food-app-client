package com.leanhquan.deliveryfoodver2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView  lottieAnimationView;
    Handler              handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lottieAnimationView = findViewById(R.id.animationView);
            }
        };

        handler.postDelayed(runnable, 1000);

        final Intent intent = new Intent(this, MainActivity.class);

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(40000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}
