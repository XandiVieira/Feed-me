package com.relyon.feedme.adapter.viewpageradapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.relyon.feedme.activity.fragment.foodtypes.ForYouFragment;
import com.relyon.feedme.activity.fragment.foodtypes.LactoseFreeFragment;
import com.relyon.feedme.activity.fragment.foodtypes.SweetFragment;
import com.relyon.feedme.activity.fragment.foodtypes.VegetarianFragment;

public class CategoriesViewPagerAdapter extends FragmentPagerAdapter {
    
    Fragment fragment;

    public CategoriesViewPagerAdapter(FragmentManager fm, Fragment fragment) {
        super(fm);
        this.fragment = fragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment newFragment = null;
        switch (position) {
            case 0:
                newFragment = new ForYouFragment(fragment.getContext(), fragment.getActivity());
                break;
            case 1:
                newFragment = new SweetFragment(fragment.getContext(), fragment.getActivity());
                break;
            case 2:
                newFragment = new VegetarianFragment(fragment.getContext(), fragment.getActivity());
                break;
            case 3:
                newFragment = new LactoseFreeFragment(fragment.getContext(), fragment.getActivity());
                break;
        }
        return newFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "Para você";
                break;
            case 1:
                title = "Doces";
                break;
            case 2:
                title = "Vegetariano";
                break;
            case 3:
                title = "Zero lactose";
                break;
        }
        return title;
    }
}