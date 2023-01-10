package com.relyon.feedme.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.adapter.viewpageradapters.RecipeViewPagerAdapter;
import com.relyon.feedme.databinding.FragmentRecipeBinding;
import com.relyon.feedme.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    private FragmentRecipeBinding binding;
    private Recipe recipe;

    public RecipeFragment(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        boolean favorite = isFavorite(recipe.getId(), binding.favorite);
        if (favorite) {
            binding.favorite.setBackgroundResource(R.drawable.ic_heart_filled);
        }

        binding.favorite.setOnClickListener(view -> {
            updateFavorite(recipe.getId(), binding.favorite, isFavorite(recipe.getId(), binding.favorite));
        });

        buildFragment();

        return root;
    }

    private boolean isFavorite(String recipe, ImageView favorite) {
        boolean contains = Util.user.getFavoriteRecipes() != null && Util.user.getFavoriteRecipes().contains(recipe);
        if (contains) {
            favorite.setBackgroundResource(R.drawable.ic_heart_filled);
        } else {
            favorite.setBackgroundResource(R.drawable.ic_heart);
        }
        return contains;
    }

    private void updateFavorite(String id, ImageView favorite, boolean isFavorite) {
        Util.db.collection("users").document(Util.user.getId()).collection("favoriteRecipes")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().getDocuments().isEmpty()) {
                        List<String> list = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            list.add(document.getId());
                        }
                    }
                    updateFavoriteListLocally(id, isFavorite, favorite);
                    updateFavoriteLisDB(Util.user.getFavoriteRecipes());
                });
    }

    private void updateFavoriteListLocally(String id, boolean isFavorite, ImageView favorite) {
        favorite.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));
        favorite.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_out));
        if (isFavorite) {
            Util.user.removeRecipeToFavorites(id);
            favorite.setBackgroundResource(R.drawable.ic_heart);
        } else {
            Util.user.addRecipeToFavorites(id);
            favorite.setBackgroundResource(R.drawable.ic_heart_filled);
        }
    }

    private void updateFavoriteLisDB(List<String> list) {
        Util.db.collection("users").document(Util.user.getId()).update("favoriteRecipes", list);
    }

    private void buildFragment() {
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(new RecipeViewPagerAdapter(getChildFragmentManager(), this, recipe));
        binding.tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(4);
        viewPager.animate().translationX(0f).setDuration(0);
    }
}