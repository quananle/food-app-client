package com.leanhquan.deliveryfoodver2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.airbnb.lottie.LottieAnimationView;


public class SplashScreen extends AppCompatActivity {

    private static int              PLASH_SCREEN_DISPLAY = 3000;
    private Handler                 handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        LottieAnimationView lottieAnimationView = findViewById(R.id.animationView);
        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.anim_splash);
        lottieAnimationView.startAnimation(animation);



        final Intent intent = new Intent(this, LoginActivity.class);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(PLASH_SCREEN_DISPLAY);
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
