package com.relyon.feedme.model;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class ViewObject {

    private int id;
    private View view;
    private EditText editText;
    private ImageButton remove;
    private ImageButton add;
    private Spinner unit;
    private EditText quantity;


    public ViewObject() {
    }

    public ViewObject(int id, View view, EditText editText, ImageButton remove, ImageButton add, Spinner unit, EditText quantity) {
        this.id = id;
        this.view = view;
        this.editText = editText;
        this.remove = remove;
        this.add = add;
        this.unit = unit;
        this.quantity = quantity;
    }

    public ViewObject(int id, View view, EditText editText, ImageButton remove, ImageButton add) {
        this.id = id;
        this.view = view;
        this.editText = editText;
        this.remove = remove;
        this.add = add;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public ImageButton getRemove() {
        return remove;
    }

    public void setRemove(ImageButton remove) {
        this.remove = remove;
    }

    public ImageButton getAdd() {
        return add;
    }

    public void setAdd(ImageButton add) {
        this.add = add;
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