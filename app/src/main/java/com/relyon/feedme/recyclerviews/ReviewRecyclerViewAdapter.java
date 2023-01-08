package com.relyon.feedme.recyclerviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.model.Review;
import com.relyon.feedme.model.User;

import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {

    private List<Review> reviews;
    private LayoutInflater mInflater;
    private final Context context;

    public ReviewRecyclerViewAdapter(Context context, List<Review> reviews) {
        this.mInflater = LayoutInflater.from(context);
        this.reviews = reviews;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.ratingBar.setRating(review.getRate());
        holder.review.setText(review.getComment());
        holder.reviewTitle.setText(review.getReviewTitle());

        Util.getDb().collection("users").document(review.getReviewerId()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    User user = document.toObject(User.class);
                    if (user != null) {
                        holder.username.setText(user.getUsername());
                    }
                }
            }
        });

        Glide.with(context).load(Util.getUser().getPhotoUrl()).circleCrop().into(holder.userPhoto);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView reviewTitle;
        TextView review;
        ImageView userPhoto;
        RatingBar ratingBar;

        ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            userPhoto = itemView.findViewById(R.id.user_photo);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            reviewTitle = itemView.findViewById(R.id.review_title);
            review = itemView.findViewById(R.id.review);
        }
    }
}