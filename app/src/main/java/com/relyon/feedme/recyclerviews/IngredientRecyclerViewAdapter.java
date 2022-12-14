package com.relyon.feedme.recyclerviews;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.relyon.feedme.R;
import com.relyon.feedme.model.Ingredient;

import java.util.List;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder> {

    private List<Ingredient> ingredients;
    private LayoutInflater mInflater;
    private final Context context;

    public IngredientRecyclerViewAdapter(Context context, List<Ingredient> ingredients) {
        this.mInflater = LayoutInflater.from(context);
        this.ingredients = ingredients;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.ingredient.setText(ingredient.getName());
        String quantityAndUnity = ingredient.getQuantity() + " " + ingredient.getUnitOfMeasurement();
        holder.quantity.setText(quantityAndUnity);

        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                holder.quantity.setPaintFlags(holder.quantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.ingredient.setPaintFlags(holder.ingredient.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.ingredient.setTextColor(context.getResources().getColor(R.color.grey_alpha));
                holder.quantity.setTextColor(context.getResources().getColor(R.color.grey_alpha));
            } else {
                holder.quantity.setPaintFlags(holder.quantity.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.ingredient.setPaintFlags(holder.ingredient.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.ingredient.setTextColor(context.getResources().getColor(R.color.black));
                holder.quantity.setTextColor(context.getResources().getColor(R.color.black));
            }
        });

        holder.quantity.setOnClickListener(view -> Toast.makeText(context, quantityAndUnity + " de " + ingredient.getName(), Toast.LENGTH_SHORT).show());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient;
        TextView quantity;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);

            ingredient = itemView.findViewById(R.id.ingredient);
            quantity = itemView.findViewById(R.id.quantity);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}