package com.relyon.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.databinding.ActivityOnBoardingBinding;
import com.relyon.feedme.recyclerviews.OnBoardingAdapter;

public class OnBoardingActivity extends AppCompatActivity implements Runnable {

    private ActivityOnBoardingBinding binding;
    private TextView[] dots;
    private OnBoardingAdapter viewPagerAdapter;
    private FirebaseAuth mAuth;
    private Handler handler;
    private Runnable runnable = this;

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
            if (position == 2) {
                binding.nextButton.setText("Cadastro");
            } else {
                binding.nextButton.setText("Continuar");
            }
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 4000);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        /*mAuth.signOut();
        GoogleSignInClient googleSignInClient;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.signOut();*/

        Util.auth = mAuth;
        Util.db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        binding.nextButton.setOnClickListener(v -> {
            if (getItem(0) < 2)
                binding.slideViewPager.setCurrentItem(getItem(1), true);
            else {
                Intent i = new Intent(OnBoardingActivity.this, RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });
        viewPagerAdapter = new OnBoardingAdapter(this);
        binding.slideViewPager.setAdapter(viewPagerAdapter);
        setDotIndicator(0);
        binding.slideViewPager.addOnPageChangeListener(viewPagerListener);
        handler = new Handler();
        handler.postDelayed(this, 4000);
    }

    public void setDotIndicator(int position) {
        dots = new TextView[3];
        binding.dotIndicator.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(50);
            dots[i].setPadding(16, 0, 16, 0);
            dots[i].setTextColor(getResources().getColor(R.color.grey_alpha, getApplicationContext().getTheme()));
            binding.dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.accent, getApplicationContext().getTheme()));
    }

    private int getItem(int i) {
        return binding.slideViewPager.getCurrentItem() + i;
    }

    @Override
    public void run() {
        if (binding.slideViewPager.getCurrentItem() == 2) {
            binding.slideViewPager.setCurrentItem(0);
        } else {
            binding.slideViewPager.setCurrentItem(binding.slideViewPager.getCurrentItem() + 1);
        }
        handler.postDelayed(this, 4000);
    }
}