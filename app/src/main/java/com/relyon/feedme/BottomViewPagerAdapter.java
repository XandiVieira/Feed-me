package com.relyon.feedme;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.relyon.feedme.activity.fragment.categories.ForYouFragment;
import com.relyon.feedme.activity.fragment.categories.LactoseFreeFragment;
import com.relyon.feedme.activity.fragment.categories.SweetFragment;
import com.relyon.feedme.activity.fragment.categories.VegetarianFragment;

public class BottomViewPagerAdapter extends FragmentPagerAdapter {

    public BottomViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ForYouFragment();
                break;
            case 1:
                fragment = new SweetFragment();
                break;
            case 2:
                fragment = new VegetarianFragment();
                break;
            case 3:
                fragment = new LactoseFreeFragment();
                break;
        }
        return fragment;
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
                title = "Perfil";
                break;
            case 1:
                title = "Ranking";
                break;
            case 2:
                title = "Home";
                break;
            case 3:
                title = "Alertas";
                break;
        }
        return title;
    }
}