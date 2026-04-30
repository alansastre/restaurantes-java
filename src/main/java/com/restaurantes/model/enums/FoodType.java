package com.restaurantes.model.enums;

public enum FoodType {
    SPANISH("Española"),
    JAPANESE("Japonesa");

    private final String label;

    FoodType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
