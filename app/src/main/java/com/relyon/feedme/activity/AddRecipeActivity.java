package com.relyon.feedme.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.databinding.ActivityAddRecipeBinding;
import com.relyon.feedme.model.Recipe;
import com.relyon.feedme.model.ViewObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AddRecipeActivity extends AppCompatActivity {

    ActivityAddRecipeBinding binding;
    List<ViewObject> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        binding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        views = new ArrayList<>();
        addView();

        binding.send.setOnClickListener(view -> {
            if (recipeDataIsValid()) {
                List<String> ingredients = new ArrayList<>();
                for (EditText editText : views.stream().map(ViewObject::getEditText).collect(Collectors.toList())) {
                    ingredients.add(editText.getText().toString());
                }
                createNewRecipe(ingredients, binding.recipeTitle.getText().toString());
            }
        });
    }

    private boolean recipeDataIsValid() {
        return true;
    }

    private void createNewRecipe(List<String> ingredients, String title) {
        Recipe recipe = new Recipe(UUID.randomUUID().toString(), Util.getUser().getId(), title, ingredients, new ArrayList<>(), 0);
    }

    private void addView() {
        final View view = getLayoutInflater().inflate(R.layout.ingredient_add_item, null, false);

        view.setId(views.size());
        EditText ingredient = view.findViewById(R.id.ingredient);
        ingredient.setId(views.size());
        ingredient.setHint("Ingredient " + (views.size() + 1));
        ImageButton add = view.findViewById(R.id.add_ingredient);
        add.setId(views.size());
        ImageButton remove = view.findViewById(R.id.remove_ingredient);
        remove.setId(views.size());
        remove.setVisibility(View.VISIBLE);

        views.add(new ViewObject(views.size(), view, ingredient, remove, add));

        add.setOnClickListener(view1 -> {
            if (checkEditTextsAreFilled()) {
                addView();
                updateLayout();
            } else {
                Toast.makeText(view.getContext(), "Insira o nome do ingrediente antes de adicionar um novo!", Toast.LENGTH_SHORT).show();
            }
        });
        remove.setOnClickListener(view1 -> {
            removeView(view);
            views.remove(view1.getId());
            updateIds(view1);
            updateLayout();
        });

        binding.ingredientsList.addView(view);
    }

    private void updateIds(View view1) {
        int id = view1.getId();
        for (int i = id; i < views.size(); i++) {
            ViewObject viewObject = views.get(i);
            int newId = viewObject.getId() != 0 ? viewObject.getId() - 1 : 0;
            viewObject.setId(newId);
            viewObject.getEditText().setId(newId);
            viewObject.getAdd().setId(newId);
            viewObject.getRemove().setId(newId);
        }

        System.out.println(view1.getId());
    }

    private void updateLayout() {
        for (ViewObject view : views) {
            if (view.getId() == views.size() - 1) {
                view.getAdd().setVisibility(View.VISIBLE);
            } else {
                view.getAdd().setVisibility(View.GONE);
            }
            view.getRemove().setVisibility(View.VISIBLE);
            if (view.getId() == 0 && views.size() == 1) {
                view.getRemove().setVisibility(View.GONE);
            } else {
                view.getRemove().setVisibility(View.VISIBLE);
            }
            int id = view.getEditText().getId();
            view.getEditText().setHint("ingredient " + (id + 1));
        }
    }

    private boolean checkEditTextsAreFilled() {
        for (EditText editText : views.stream().map(ViewObject::getEditText).collect(Collectors.toList())) {
            if (editText.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void removeView(View view) {
        binding.ingredientsList.removeView(view);
    }
}