package com.relyon.feedme.model;

public class Review {

    private String id;
    private String receiptId;
    private double rate;
    private String comment;

    public Review(String id, String receiptId, double rate, String comment) {
        this.id = id;
        this.receiptId = receiptId;
        this.rate = rate;
        this.comment = comment;
    }
}
