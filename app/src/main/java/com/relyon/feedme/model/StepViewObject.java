package com.relyon.feedme.model;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class StepViewObject extends ViewObject {

    public StepViewObject(int id, View view, EditText editText, ImageButton remove, ImageButton add) {
        super(id, view, editText, remove, add);
    }
}