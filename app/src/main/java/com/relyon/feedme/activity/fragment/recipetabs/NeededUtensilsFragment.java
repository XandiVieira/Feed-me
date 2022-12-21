package com.relyon.feedme.activity.fragment.recipetabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.relyon.feedme.R;
import com.relyon.feedme.model.Recipe;
import com.relyon.feedme.recyclerviews.PreparationRecyclerViewAdapter;
import com.relyon.feedme.recyclerviews.UtensilRecyclerViewAdapter;

public class NeededUtensilsFragment extends Fragment {

    private UtensilRecyclerViewAdapter adapter;
    private Context context;
    private Activity activity;
    private Recipe recipe;

    public NeededUtensilsFragment(Context context, Activity activity, Recipe recipe) {
        this.context = context;
        this.activity = activity;
        this.recipe = recipe;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_needed_utensils, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.utensils_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new UtensilRecyclerViewAdapter(getContext(), recipe.getUtensilsNeeded());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
