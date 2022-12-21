package com.relyon.feedme.model;

import java.io.Serializable;
import java.util.UUID;

public class Review implements Serializable {

    private String id;
    private String reviewerId;
    private String recipeId;
    private double rate;
    private String reviewTitle;
    private String comment;

    public Review(String reviewerId, String recipeId, double rate, String reviewTitle, String comment) {
        this.id = UUID.randomUUID().toString();
        this.reviewerId = reviewerId;
        this.recipeId = recipeId;
        this.rate = rate;
        this.reviewTitle = reviewTitle;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
