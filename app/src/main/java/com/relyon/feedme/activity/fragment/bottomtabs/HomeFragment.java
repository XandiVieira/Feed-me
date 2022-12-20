package com.relyon.feedme.activity.fragment.bottomtabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.relyon.feedme.viewpageradapters.CategoriesViewPagerAdapter;
import com.relyon.feedme.activity.AddRecipeActivity;
import com.relyon.feedme.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.searchButton.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AddRecipeActivity.class));
        });

        buildFragment();

        return root;
    }

    private void buildFragment() {
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(new CategoriesViewPagerAdapter(getChildFragmentManager(), this));
        binding.tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(4);
        viewPager.animate().translationX(0f).setDuration(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}