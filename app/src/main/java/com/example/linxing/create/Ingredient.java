package com.example.linxing.create;

/**
 * Created by linxing on 3/16/18.
 */

class Ingredient {

    private String food_name;
    private String image;
    private String serving_unit;
    private String brand_name_item_name;
    private String nf_calories;
    private String brand_name;
    private String nix_item_id;

    public Ingredient(String food_name, String image, String serving_unit, String brand_name_item_name, String nf_calories, String brand_name, String nix_item_id) {
        this.food_name = food_name;
        this.image = image;
        this.serving_unit = serving_unit;
        this.brand_name_item_name = brand_name_item_name;
        this.nf_calories = nf_calories;
        this.brand_name = brand_name;
        this.nix_item_id = nix_item_id;
    }

    public Ingredient(String food_name, String image, String serving_unit, String nf_calories, String brand_name, String nix_item_id) {
        this.food_name = food_name;
        this.image = image;
        this.serving_unit = serving_unit;
        this.nf_calories = nf_calories;
        this.brand_name = brand_name;
        this.nix_item_id = nix_item_id;
    }

    public Ingredient() {

    }
    public String getFood_name() {
        return food_name;
    }

    public String getImage() {
        return image;
    }

    public String getServing_unit() {
        return serving_unit;
    }

    public String getBrand_name_item_name() {
        return brand_name_item_name;
    }

    public String getNf_calories() {
        return nf_calories;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setNix_item_id(String id) {
        nix_item_id = id;
    }

    public String getNix_item_id() {
        return nix_item_id;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "food_name='" + food_name + '\'' +
                ", image='" + image + '\'' +
                ", serving_unit='" + serving_unit + '\'' +
                ", brand_name_item_name='" + brand_name_item_name + '\'' +
                ", nf_calories='" + nf_calories + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", nix_item_id='" + nix_item_id + '\'' +
                '}';
    }
}
