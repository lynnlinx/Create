package com.example.linxing.create;

import java.util.ArrayList;

public class RecipeDetailItem {
    private String title;
    private String dishType;
    private String diet;
    private String image;
    private String imageType;
    private int minutes;
    private int servings;
    private ArrayList<RecipeIngredient> RecipeIngredients;
    private ArrayList<String> instructions;

    public RecipeDetailItem(String title, String dishType, String diet, String image,
                            String imageType, int minutes, int servings,
                            ArrayList<RecipeIngredient> recipeIngredients,
                            ArrayList<String> instructions) {
        this.title = title;
        this.dishType = dishType;
        this.diet = diet;
        this.image = image;
        this.imageType = imageType;
        this.minutes = minutes;
        this.servings = servings;
        RecipeIngredients = recipeIngredients;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public String getDishType() {
        return dishType;
    }

    public String getDiet() {
        return diet;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getServings() {
        return servings;
    }

    public ArrayList<RecipeIngredient> getRecipeIngredients() {
        return RecipeIngredients;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (RecipeIngredient r : RecipeIngredients) {
            sb.append(r.getIngreName()).append("  ")
                    .append(r.getCount()).append(" ")
                    .append(r.getUnit()).append("\n");
        }
        return "RecipeIngredients: \n"  + sb.toString() +
                "\n Instructions: \n" + instructions;
    }
}
