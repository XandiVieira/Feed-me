package com.relyon.feedme.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String username;
    private String email;
    private String memberSince;
    private double points;
    private List<String> favoriteRecipes;
    private String photoUrl;

    public User() {
    }

    public User(String id, String username, String email, String memberSince, double points, String photoUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.memberSince = memberSince;
        this.points = points;
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public List<String> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<String> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public void addRecipeToFavorites(String recipeId) {
        if (favoriteRecipes == null) {
            favoriteRecipes = new ArrayList<>();
        }
        favoriteRecipes.add(recipeId);
    }

    public void removeRecipeToFavorites(String recipeId) {
        if (favoriteRecipes == null) {
            favoriteRecipes = new ArrayList<>();
        }
        favoriteRecipes.remove(recipeId);
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}