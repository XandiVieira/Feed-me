package com.relyon.feedme.model;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private String name;
    private String unitOfMeasurement;
    private Integer quantity;

    public Ingredient() {
    }

    public Ingredient(String name, String unitOfMeasurement, Integer quantity) {
        this.name = name;
        this.unitOfMeasurement = unitOfMeasurement;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}