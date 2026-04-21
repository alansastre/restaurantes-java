package com.restaurantes.model.enums;

public enum DishType {
    STARTER("Entrantes"),
    MAIN("Principales"),
    DESSERT("Postres");

    private final String label;

    DishType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
