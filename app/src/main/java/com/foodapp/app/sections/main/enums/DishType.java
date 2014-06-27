package com.foodapp.app.sections.main.enums;

/* Defines the type of dish */
public enum DishType {
    NONE("none"),
    APPETIZER("appetizer"),
    PASTA_PIZZA("pasta_pizza"),
    SOUP("soup"),
    MEAT_MAIN_COURSE("meat_main_course"),
    FISH_MAIN_COURSE("fish_main_course"),
    VEGETARIAN_MAIN_DISH("vegetarian_main_course"),
    SIDE("side"),
    SALAD("salad"),
    DESSERT("dessert");

    public static final String DISH_TYPE = "DISH_TYPE";
    private String mDishType;

    private DishType(String dishType) {
        mDishType = dishType;
    }

    public String getDishType() {
        return mDishType;
    }
}