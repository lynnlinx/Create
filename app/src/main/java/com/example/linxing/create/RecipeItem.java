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


    public RecipeItem(int id, String title, int usedIngredientCount, int missedIngredientCount, String image, String imageType) {
        this.id = id;
        this.title = title;
        this.usedIngredientCount = usedIngredientCount;
        this.missedIngredientCount = missedIngredientCount;
        this.image = image;
        this.imageType = imageType;
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

    @Override
    public String toString() {
        return "RecipeItem{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", imageType='" + imageType + '\'' +
                ", title='" + title + '\'' +
                ", usedIngredientCount='" + usedIngredientCount + '\'' +
                ", missedIngredientCount='" + missedIngredientCount + '\'' +
                '}';
    }
}
