package com.relyon.feedme.activity.fragment.foodtypes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.model.Recipe;
import com.relyon.feedme.recyclerviews.RecipeRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ForYouFragment extends Fragment {

    private RecipeRecyclerViewAdapter adapter;
    private Context context;
    private Activity activity;

    public ForYouFragment(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public ForYouFragment() {
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
        Util.db.collection("recipes").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Recipe> recipes = new ArrayList<>();
                task.getResult().forEach(result -> {
                    recipes.add(result.toObject(Recipe.class));
                });
                adapter = new RecipeRecyclerViewAdapter(getContext(), activity, recipes);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}