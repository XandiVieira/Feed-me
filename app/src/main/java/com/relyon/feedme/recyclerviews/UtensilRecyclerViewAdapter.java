package com.relyon.feedme.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.relyon.feedme.R;

import java.util.List;

public class UtensilRecyclerViewAdapter extends RecyclerView.Adapter<UtensilRecyclerViewAdapter.ViewHolder> {

    private List<String> utensils;
    private LayoutInflater mInflater;
    private final Context context;

    public UtensilRecyclerViewAdapter(Context context, List<String> utensils) {
        this.mInflater = LayoutInflater.from(context);
        this.utensils = utensils;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_utensil, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String utensil = utensils.get(position);
        holder.utensil.setText(utensil);
        holder.utensil.setBackgroundResource(R.drawable.home_background);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return utensils.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView utensil;
        ImageView utensilPhoto;

        ViewHolder(View itemView) {
            super(itemView);

            utensil = itemView.findViewById(R.id.utensil);
            utensilPhoto = itemView.findViewById(R.id.utensil_photo);
        }
    }
}