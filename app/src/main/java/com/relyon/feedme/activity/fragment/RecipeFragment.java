package com.relyon.feedme.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.relyon.feedme.databinding.FragmentRecipeBinding;
import com.relyon.feedme.model.Recipe;
import com.relyon.feedme.viewpageradapters.RecipeViewPagerAdapter;

public class RecipeFragment extends Fragment {

    private FragmentRecipeBinding binding;
    private Recipe recipe;

    public RecipeFragment(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buildFragment();

        return root;
    }

    private void buildFragment() {
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(new RecipeViewPagerAdapter(getChildFragmentManager(), this, recipe));
        binding.tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(4);
        viewPager.animate().translationX(0f).setDuration(0);
    }
}