package com.relyon.feedme.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.relyon.feedme.R;

import java.util.List;

public class PreparationRecyclerViewAdapter extends RecyclerView.Adapter<PreparationRecyclerViewAdapter.ViewHolder> {

    private List<String> preparationSteps;
    private LayoutInflater mInflater;
    private final Context context;

    public PreparationRecyclerViewAdapter(Context context, List<String> preparationSteps) {
        this.mInflater = LayoutInflater.from(context);
        this.preparationSteps = preparationSteps;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_preparation, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String preparationStep = preparationSteps.get(position);
        holder.step.setText(preparationStep);
        holder.stepNumber.setText((position + 1) + ".");
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return preparationSteps.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView step;
        TextView stepNumber;

        ViewHolder(View itemView) {
            super(itemView);

            stepNumber = itemView.findViewById(R.id.step_number);
            step = itemView.findViewById(R.id.step);
        }
    }
}