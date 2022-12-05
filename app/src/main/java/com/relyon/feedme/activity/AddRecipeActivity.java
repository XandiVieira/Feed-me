package com.relyon.feedme.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    List<ViewObject> ingredientsViews;
    List<ViewObject> stepsViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        binding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ingredientsViews = new ArrayList<>();
        stepsViews = new ArrayList<>();
        addIngredientView();
        addStepView();

        binding.send.setOnClickListener(view -> {
            if (recipeDataIsValid()) {
                List<String> ingredients = new ArrayList<>();
                for (EditText editText : ingredientsViews.stream().map(ViewObject::getEditText).collect(Collectors.toList())) {
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

        saveNewRecipe(recipe);
    }

    private void saveNewRecipe(Recipe recipe) {
        Util.getDb().collection("recipes").document(recipe.getId())
                .set(recipe);
    }

    private void addIngredientView() {
        final View view = getLayoutInflater().inflate(R.layout.ingredient_add_item, null, false);

        view.setId(ingredientsViews.size());
        EditText ingredient = view.findViewById(R.id.ingredient);
        ingredient.setId(ingredientsViews.size());
        ingredient.setHint("Ingredient " + (ingredientsViews.size() + 1));
        ImageButton add = view.findViewById(R.id.add_ingredient);
        add.setId(ingredientsViews.size());
        ImageButton remove = view.findViewById(R.id.remove_ingredient);
        remove.setId(ingredientsViews.size());

        ingredientsViews.add(new ViewObject(ingredientsViews.size(), view, ingredient, remove, add));

        add.setOnClickListener(view1 -> {
            if (checkEditTextsAreFilled(ingredientsViews)) {
                addIngredientView();
                updateLayout(ingredientsViews, "ingredient ");
            } else {
                Toast.makeText(view.getContext(), "Insira o nome do ingrediente antes de adicionar um novo!", Toast.LENGTH_SHORT).show();
            }
        });
        remove.setOnClickListener(view1 -> {
            removeView(view, binding.ingredientsList);
            ingredientsViews.remove(view1.getId());
            updateIds(view1, ingredientsViews);
            updateLayout(ingredientsViews, "Ingredient ");
        });

        binding.ingredientsList.addView(view);
    }

    private void addStepView() {
        final View view = getLayoutInflater().inflate(R.layout.ingredient_add_item, null, false);

        view.setId(stepsViews.size());
        EditText ingredient = view.findViewById(R.id.ingredient);
        ingredient.setId(stepsViews.size());
        ingredient.setHint("Step " + (stepsViews.size() + 1));
        ImageButton add = view.findViewById(R.id.add_ingredient);
        add.setId(stepsViews.size());
        ImageButton remove = view.findViewById(R.id.remove_ingredient);
        remove.setId(stepsViews.size());

        stepsViews.add(new ViewObject(stepsViews.size(), view, ingredient, remove, add));

        add.setOnClickListener(view1 -> {
            if (checkEditTextsAreFilled(stepsViews)) {
                addStepView();
                updateLayout(stepsViews, "Step ");
            } else {
                Toast.makeText(view.getContext(), "Insira o passo antes de adicionar um novo!", Toast.LENGTH_SHORT).show();
            }
        });
        remove.setOnClickListener(view1 -> {
            removeView(view, binding.stepsList);
            stepsViews.remove(view1.getId());
            updateIds(view1, stepsViews);
            updateLayout(stepsViews, "Step ");
        });

        binding.stepsList.addView(view);
    }

    private void updateIds(View view1, List<ViewObject> list) {
        int id = view1.getId();
        for (int i = id; i < list.size(); i++) {
            ViewObject viewObject = list.get(i);
            int newId = viewObject.getId() != 0 ? viewObject.getId() - 1 : 0;
            viewObject.setId(newId);
            viewObject.getEditText().setId(newId);
            viewObject.getAdd().setId(newId);
            viewObject.getRemove().setId(newId);
        }

        System.out.println(view1.getId());
    }

    private void updateLayout(List<ViewObject> list, String hint) {
        for (ViewObject view : list) {
            if (view.getId() == list.size() - 1) {
                view.getAdd().setVisibility(View.VISIBLE);
            } else {
                view.getAdd().setVisibility(View.GONE);
            }
            view.getRemove().setVisibility(View.VISIBLE);
            if (view.getId() == 0 && list.size() == 1) {
                view.getRemove().setVisibility(View.GONE);
            } else {
                view.getRemove().setVisibility(View.VISIBLE);
            }
            int id = view.getEditText().getId();
            view.getEditText().setHint(hint + (id + 1));
        }
    }

    private boolean checkEditTextsAreFilled(List<ViewObject> list) {
        for (EditText editText : list.stream().map(ViewObject::getEditText).collect(Collectors.toList())) {
            if (editText.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void removeView(View view, LinearLayout list) {
        list.removeView(view);
    }
}