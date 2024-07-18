package com.example.KEBProject.entity;

public enum Rating {
    RATING_1("1"),
    RATING_1_5("1.5"),
    RATING_2("2"),
    RATING_2_5("2.5"),
    RATING_3("3"),
    RATING_3_5("3.5"),
    RATING_4("4"),
    RATING_4_5("4.5"),
    RATING_5("5");

    private final String value;

    Rating(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}