package com.relyon.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.relyon.feedme.R;
import com.relyon.feedme.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this,
                    OnBoardingActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, SPLASH_TIME_OUT);
    }
}