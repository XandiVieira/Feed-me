package com.relyon.feedme.model;

import java.time.LocalDate;

public class User {

    public User() {
    }

    public User(String id, String username, String email, String memberSince, double points) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.memberSince = memberSince;
        this.points = points;
    }

    private String id;
    private String username;
    private String email;
    private String memberSince;
    private double points;

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
}