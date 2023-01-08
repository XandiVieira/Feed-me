package com.relyon.feedme.activity.fragment.foodtypes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.relyon.feedme.R;


public class SweetFragment extends Fragment {

    private Context context;
    private Activity activity;

    public SweetFragment() {
    }

    public SweetFragment(Context context, Activity activity) {
        // Required empty public constructor
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sweet, container, false);
    }
}