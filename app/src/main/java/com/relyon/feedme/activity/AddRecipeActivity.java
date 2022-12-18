package com.relyon.feedme.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.databinding.ActivityAddRecipeBinding;
import com.relyon.feedme.model.Ingredient;
import com.relyon.feedme.model.IngredientViewObject;
import com.relyon.feedme.model.Recipe;
import com.relyon.feedme.model.StepViewObject;
import com.relyon.feedme.model.UnitOfMeasurement;
import com.relyon.feedme.model.ViewObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddRecipeActivity extends AppCompatActivity {

    ActivityAddRecipeBinding binding;
    List<ViewObject> ingredientsViews;
    List<ViewObject> stepsViews;
    List<UnitOfMeasurement> unitOfMeasurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        binding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ingredientsViews = new ArrayList<>();
        stepsViews = new ArrayList<>();

        getUnitsOfMeasurement();

        binding.send.setOnClickListener(view -> {
            if (recipeDataIsValid()) {
                List<Ingredient> ingredients = new ArrayList<>();
                List<String> steps = new ArrayList<>();

                ingredientsViews.forEach(item -> {
                    ingredients.add(new Ingredient(item.getEditText().getText().toString(), item.getUnit().getSelectedItem().toString(), Integer.valueOf(item.getQuantity().getText().toString())));
                });

                stepsViews.stream().map(ViewObject::getEditText).collect(Collectors.toList()).forEach(step -> steps.add(step.getText().toString()));

                RadioButton radioButton = findViewById(binding.difficulty.getCheckedRadioButtonId());
                String difficulty = radioButton.getText().toString();

                createNewRecipe(ingredients, steps, binding.recipeTitle.getText().toString(), binding.simpleSeekBar.getProgress(), binding.observations.getText().toString().trim(), difficulty);
            }
        });

        binding.simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.timeIndicator.setText(i + " min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void getUnitsOfMeasurement() {
        Util.getDb().collection("units").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                unitOfMeasurement = new ArrayList<>();
                task.getResult().forEach(result -> {
                    unitOfMeasurement.add(result.toObject(UnitOfMeasurement.class));
                });
                addItemView(ingredientsViews, "Ingredient ", binding.ingredientsList);
                addItemView(stepsViews, "Step ", binding.stepsList);
            }
        });
    }

    private boolean recipeDataIsValid() {
        return !binding.observations.getText().toString().trim().isEmpty();
    }

    private void createNewRecipe(List<Ingredient> ingredients, List<String> steps, String title, int time, String observations, String difficulty) {
        Recipe recipe = new Recipe(Util.getUser().getId(), title, ingredients, steps, time, observations, difficulty);
        saveNewRecipe(recipe);
    }

    private void saveNewRecipe(Recipe recipe) {
        Util.getDb().collection("recipes").document(recipe.getId())
                .set(recipe);
        Toast.makeText(getApplicationContext(), "Receita criada!", Toast.LENGTH_SHORT).show();
    }

    private void addItemView(List<ViewObject> lista, String hint, LinearLayout layout) {
        final View view = getLayoutInflater().inflate(R.layout.item_add_item, null, false);

        view.setId(lista.size());
        EditText ingredientOrStep = view.findViewById(R.id.item);
        ingredientOrStep.setId(lista.size());
        ingredientOrStep.setHint(hint + (lista.size() + 1));
        ImageButton add = view.findViewById(R.id.add_item);
        add.setId(lista.size());
        ImageButton remove = view.findViewById(R.id.remove_item);
        remove.setId(lista.size());

        LinearLayout linearLayout = view.findViewById(R.id.measure_layout);

        if (hint.equals("Ingredient ")) {
            linearLayout.setVisibility(View.VISIBLE);
            Spinner units = view.findViewById(R.id.measure_unity);
            units.setId(lista.size());
            EditText quantity = view.findViewById(R.id.quantity);
            quantity.setId(lista.size());
            lista.add(new IngredientViewObject(lista.size(), view, ingredientOrStep, remove, add, units, quantity));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, unitOfMeasurement.stream().map(UnitOfMeasurement::getUnitName).collect(Collectors.toList()));
            units.setAdapter(adapter);
            units.setSelection(0);
        } else {
            linearLayout.setVisibility(View.GONE);
            LinearLayout buttonsLayout = view.findViewById(R.id.buttons_layout);
            buttonsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    4));
            lista.add(new StepViewObject(lista.size(), view, ingredientOrStep, remove, add));
        }

        add.setOnClickListener(view1 -> {
            if (checkEditTextsAreFilled(lista)) {
                addItemView(lista, hint, layout);
                updateLayout(lista, hint);
            } else {
                Toast.makeText(view.getContext(), "Preencha as informações do " + hint + "antes de adicionar um novo!", Toast.LENGTH_SHORT).show();
            }
        });
        remove.setOnClickListener(view1 -> {
            removeView(view, layout);
            lista.remove(view1.getId());
            updateIds(view1, lista);
            updateLayout(lista, hint);
        });

        layout.addView(view);
        System.out.println();
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
        for (ViewObject object : list) {
            if (object.getEditText().getText().toString().isEmpty()) {
                if (object.getUnit() != null && object.getQuantity() != null) {
                    if (object.getUnit().getSelectedItem().toString().trim().isEmpty() || object.getQuantity().getText().toString().trim().isEmpty()) {
                        return false;
                    }
                }
                return false;
            }
        }
        return true;
    }

    private void removeView(View view, LinearLayout list) {
        list.removeView(view);
    }
}