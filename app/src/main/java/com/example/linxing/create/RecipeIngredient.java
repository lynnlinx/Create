package com.example.linxing.create;

public class RecipeIngredient {
    private int count;
    private String name;
    private String unit;

    public RecipeIngredient(int count, String name, String unit) {
        this.count = count;
        this.name = name;
        this.unit = unit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIngreName() {
        return name;
    }

    public void setIngreName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", count='" + count + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}


