package com.example.linxing.create;

/**
 * Created by linxing on 5/1/18.
 */

public class RecipeItem {
    private int id;
    private String title;
    private int usedIngredientCount;
    private int missedIngredientCount;
    private String image;
    private String imageType;
    private int calories;
    private String protein;
    private String fat;
    private String carbs;


    public RecipeItem(int id, String title, int usedIngredientCount, int missedIngredientCount, String image,
                      String imageType, int calories, String protein, String fat, String carbs) {
        this.id = id;
        this.title = title;
        this.usedIngredientCount = usedIngredientCount;
        this.missedIngredientCount = missedIngredientCount;
        this.image = image;
        this.imageType = imageType;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }


    public RecipeItem() {

    }
    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public int getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public int getCalories() {
        return calories;
    }

    public String getProtein() {
        return protein;
    }

    public String getFat() {
        return fat;
    }

    public String getCarbs() {
        return carbs;
    }

    @Override
    public String toString() {
        return "RecipeItem{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", imageType='" + imageType + '\'' +
                ", title='" + title + '\'' +
                ", usedIngredientCount='" + usedIngredientCount + '\'' +
                ", missedIngredientCount='" + missedIngredientCount + '\'' +
                ", calories='" + calories + '\'' +
                ", protein='" + protein + '\'' +
                ", fat='" + fat + '\'' +
                ", carbs='" + carbs + '\'' +
                '}';
    }
}
