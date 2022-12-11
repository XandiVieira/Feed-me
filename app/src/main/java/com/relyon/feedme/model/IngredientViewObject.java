package com.relyon.feedme.model;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class IngredientViewObject extends ViewObject {

    private Spinner unit;
    private EditText quantity;

    public IngredientViewObject() {
    }

    public IngredientViewObject(int id, View view, EditText editText, ImageButton remove, ImageButton add, Spinner unit, EditText quantity) {
        super(id, view, editText, remove, add);
        this.unit = unit;
        this.quantity = quantity;
    }

    public Spinner getUnit() {
        return unit;
    }

    public void setUnit(Spinner unit) {
        this.unit = unit;
    }

    public EditText getQuantity() {
        return quantity;
    }

    public void setQuantity(EditText quantity) {
        this.quantity = quantity;
    }
}