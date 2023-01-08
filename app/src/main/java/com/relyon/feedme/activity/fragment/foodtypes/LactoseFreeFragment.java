package com.relyon.feedme.activity.fragment.foodtypes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.relyon.feedme.R;

public class LactoseFreeFragment extends Fragment {

    private Context context;
    private Activity activity;

    public LactoseFreeFragment() {
    }

    public LactoseFreeFragment(Context context, Activity activity) {
        this.context = context;
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_lactose_free, container, false);
    }
}