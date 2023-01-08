package com.relyon.feedme.activity.fragment.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.relyon.feedme.R;

public class FragmentOnBoarding3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (ViewGroup) inflater.inflate(
                R.layout.fragment_onboarding3, container, false);
    }
}