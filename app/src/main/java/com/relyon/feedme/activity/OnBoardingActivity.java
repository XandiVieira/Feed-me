package com.relyon.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.relyon.feedme.R;
import com.relyon.feedme.databinding.ActivityOnBoardingBinding;
import com.relyon.feedme.recyclerviews.OnBoardingAdapter;

public class OnBoardingActivity extends AppCompatActivity implements Runnable {

    private ActivityOnBoardingBinding binding;
    private TextView[] dots;
    private OnBoardingAdapter viewPagerAdapter;

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
        binding.nextButton.setOnClickListener(v -> {
            if (getItem(0) < 2)
                binding.slideViewPager.setCurrentItem(getItem(1), true);
            else {
                Intent i = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        viewPagerAdapter = new OnBoardingAdapter(this);
        binding.slideViewPager.setAdapter(viewPagerAdapter);
        setDotIndicator(0);
        binding.slideViewPager.addOnPageChangeListener(viewPagerListener);
        new Handler().postDelayed(this, 4000);
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
        new Handler().postDelayed(this, 5500);
    }
}