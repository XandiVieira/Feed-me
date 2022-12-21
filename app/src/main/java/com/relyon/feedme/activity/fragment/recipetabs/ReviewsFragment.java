package com.relyon.feedme.activity.fragment.recipetabs;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.relyon.feedme.model.Recipe;

public class ReviewsFragment extends Fragment {

    private Context context;
    private Activity activity;
    private Recipe recipe;

    public ReviewsFragment(Context context, Activity activity, Recipe recipe) {
        this.context = context;
        this.activity = activity;
        this.recipe = recipe;
    }
}
