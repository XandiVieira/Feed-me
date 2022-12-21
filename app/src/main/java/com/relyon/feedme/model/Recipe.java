package com.relyon.feedme.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Recipe implements Serializable {

    private String id;
    private String userId;
    private String name;
    private List<Ingredient> ingredients;
    private List<String> stepByStep;
    private List<String> utensilsNeeded;
    private List<Review> reviews;
    private int preparationTime;
    private String observations;
    private Float rate;
    private int numberOfRates;
    private Long creationDate;
    private String difficulty;
    private String photoUrl;

    public Recipe() {
    }

    public Recipe(String userId, String name, List<Ingredient> ingredients, List<String> stepByStep, List<String> utensilsNeeded, List<Review> reviews, int preparationTime, String observations, String difficulty) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.name = name;
        this.ingredients = ingredients;
        this.stepByStep = stepByStep;
        this.utensilsNeeded = utensilsNeeded;
        this.reviews = reviews;
        this.preparationTime = preparationTime;
        this.observations = observations;
        this.numberOfRates = 0;
        this.rate = 1.0f;
        this.creationDate = new Date().getTime();
        this.difficulty = difficulty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getStepByStep() {
        return stepByStep;
    }

    public void setStepByStep(List<String> stepByStep) {
        this.stepByStep = stepByStep;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public int getNumberOfRates() {
        return numberOfRates;
    }

    public void setNumberOfRates(int numberOfRates) {
        this.numberOfRates = numberOfRates;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<String> getUtensilsNeeded() {
        return utensilsNeeded;
    }

    public void setUtensilsNeeded(List<String> utensilsNeeded) {
        this.utensilsNeeded = utensilsNeeded;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}