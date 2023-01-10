package com.relyon.feedme.recyclerviews;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.activity.MainActivity;
import com.relyon.feedme.activity.fragment.RecipeFragment;
import com.relyon.feedme.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private LayoutInflater mInflater;
    private final Context context;
    private Activity activity;
    private Animation animZoomIn;
    private Animation animZoomOut;

    public RecipeRecyclerViewAdapter(Context context, Activity activity, List<Recipe> recipes) {
        this.mInflater = LayoutInflater.from(context);
        this.recipes = recipes;
        this.context = context;
        this.activity = activity;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recipe_card, parent, false);
        animZoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
        animZoomOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.title.setText(recipe.getName());
        holder.preparationTime.setText(recipe.getPreparationTime() + " min");
        holder.ratingBar.setRating(recipe.getRate());
        holder.rateIndicator.setText(String.valueOf(recipe.getRate()));
        holder.difficulty.setText(recipe.getDifficulty());

        switch (recipe.getDifficulty()) {
            case "Difícil":
                holder.difficulty.setBackgroundResource(R.color.accent);
                break;
            case "Média":
                holder.difficulty.setBackgroundResource(R.color.green);
                break;
            case "Fácil":
                holder.difficulty.setBackgroundResource(R.color.green_dark);
                break;
        }

        boolean favorite = isFavorite(recipe.getId(), holder.favorite);
        if (favorite) {
            holder.favorite.setBackgroundResource(R.drawable.ic_heart_filled);
        }

        holder.favoriteLayout.setOnClickListener(view -> {
            updateFavorite(recipe.getId(), holder.favorite, isFavorite(recipe.getId(), holder.favorite));
        });

        holder.cardView.setOnClickListener(view -> {
            ((MainActivity) activity).replaceFragment(new RecipeFragment(recipe));
        });
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
        favorite.startAnimation(animZoomIn);
        favorite.startAnimation(animZoomOut);
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

    private boolean isFavorite(String recipe, ImageView favorite) {
        boolean contains = Util.user.getFavoriteRecipes() != null && Util.user.getFavoriteRecipes().contains(recipe);
        if (contains) {
            favorite.setBackgroundResource(R.drawable.ic_heart_filled);
        } else {
            favorite.setBackgroundResource(R.drawable.ic_heart);
        }
        return contains;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView preparationTime;
        TextView rateIndicator;
        RatingBar ratingBar;
        ImageView favorite;
        Button difficulty;
        LinearLayout favoriteLayout;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recipe_title);
            preparationTime = itemView.findViewById(R.id.recipe_preparation_time);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            rateIndicator = itemView.findViewById(R.id.rate_indicator);
            favorite = itemView.findViewById(R.id.favorite);
            difficulty = itemView.findViewById(R.id.difficulty);
            favoriteLayout = itemView.findViewById(R.id.favorite_layout);
            cardView = itemView.findViewById(R.id.card);
        }

    }
}