package com.relyon.feedme.model.enums;

public enum RecipeCategoryEnum {

    SWEET,
    VEGETARIAN,
    FITNESS,
    LACTOSE_FREE;

    public String getNameFormatted(RecipeCategoryEnum opt) {

        switch (opt) {
            case SWEET:
                return "Doce";
            case VEGETARIAN:
                return "Vegetariano";
            case FITNESS:
                return "Fitness";
            case LACTOSE_FREE:
                return "Zero Lactose";
        }
        return "";
    }
}

