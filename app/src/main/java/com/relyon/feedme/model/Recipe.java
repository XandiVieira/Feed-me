package com.relyon.feedme.model;

import java.util.List;

public class Recipe {

    private String id;
    private String userId;
    private String name;
    private List<Ingredient> ingredients;
    private List<String> stepByStep;
    private int preparationTime;
    private String observations;

    public Recipe(String id, String userId, String name, List<Ingredient> ingredients, List<String> stepByStep, int preparationTime, String observations) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.ingredients = ingredients;
        this.stepByStep = stepByStep;
        this.preparationTime = preparationTime;
        this.observations = observations;
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
}
