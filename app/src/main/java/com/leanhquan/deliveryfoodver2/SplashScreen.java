package com.leanhquan.deliveryfoodver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leanhquan.deliveryfoodver2.Common.Common;
import com.leanhquan.deliveryfoodver2.Model.User;

import io.paperdb.Paper;


public class SplashScreen extends AppCompatActivity {

    private static int              PLASH_SCREEN_DISPLAY = 3000;
    private Handler                 handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        Paper.init(this);

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
