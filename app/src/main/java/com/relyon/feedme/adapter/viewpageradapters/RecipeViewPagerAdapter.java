package com.relyon.feedme.adapter.viewpageradapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.relyon.feedme.activity.fragment.recipemenu.IngredientsFragment;
import com.relyon.feedme.activity.fragment.recipemenu.NeededUtensilsFragment;
import com.relyon.feedme.activity.fragment.recipemenu.PreparationFragment;
import com.relyon.feedme.activity.fragment.recipemenu.ReviewsFragment;
import com.relyon.feedme.model.Recipe;

public class RecipeViewPagerAdapter extends FragmentStatePagerAdapter {

    Fragment fragment;
    Recipe recipe;

    public RecipeViewPagerAdapter(FragmentManager fm, Fragment fragment, Recipe recipe) {
        super(fm);
        this.fragment = fragment;
        this.recipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment newFragment = null;
        switch (position) {
            case 0:
                newFragment = new IngredientsFragment(fragment.getContext(), fragment.getActivity(), recipe);
                break;
            case 1:
                newFragment = new PreparationFragment(fragment.getContext(), fragment.getActivity(), recipe);
                break;
            case 2:
                newFragment = new NeededUtensilsFragment(fragment.getContext(), fragment.getActivity(), recipe);
                break;
            case 3:
                newFragment = new ReviewsFragment(fragment.getContext(), fragment.getActivity(), recipe);
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
                title = "Ingredientes";
                break;
            case 1:
                title = "Modo de Preparo";
                break;
            case 2:
                title = "Aparelhos Necessários";
                break;
            case 3:
                title = "Avaliações";
                break;
        }
        return title;
    }
}