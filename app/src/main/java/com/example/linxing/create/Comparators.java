package com.example.linxing.create;

import java.util.Comparator;


/**
 * Created by jiana on 2018/5/2.
 */

public class Comparators {
    public class CaloriesComparator implements Comparator<RecipeItem> {
        @Override
        public int compare(RecipeItem item1, RecipeItem item2) {
            return item2.getCalories() - item1.getCalories();
        }
    }
    public class UsedIngredientComparator implements Comparator<RecipeItem> {
        @Override
        public int compare(RecipeItem item1, RecipeItem item2) {
            return item1.getUsedIngredientCount() - item2.getUsedIngredientCount();
        }
    }
    public class MissedIngredientComparator implements Comparator<RecipeItem> {
        @Override
        public int compare(RecipeItem item1, RecipeItem item2) {
            return item1.getMissedIngredientCount() - item2.getMissedIngredientCount();
        }
    }
}
