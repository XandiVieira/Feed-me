package com.relyon.feedme.activity.fragment.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.relyon.feedme.R;
import com.relyon.feedme.RecipeRecyclerViewAdapter;
import com.relyon.feedme.Util;
import com.relyon.feedme.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ForYouFragment extends Fragment {

    private RecipeRecyclerViewAdapter adapter;

    public ForYouFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_you, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.for_you_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getRecipesFromDB(recyclerView);

        return view;
    }

    private void getRecipesFromDB(RecyclerView recyclerView) {
        Util.getDb().collection("recipes").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Recipe> recipes = new ArrayList<>();
                task.getResult().forEach(result -> {
                    recipes.add(result.toObject(Recipe.class));
                });
                adapter = new RecipeRecyclerViewAdapter(getContext(), recipes);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}