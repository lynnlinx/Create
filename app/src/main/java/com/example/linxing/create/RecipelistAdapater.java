package com.example.linxing.create;

/**
 * Created by xingzhiwang on 2/27/18.
 */

public class RecipelistAdapater {

    // adapter class
        public  int ID;
        public  String RecipeTitle;
        public  String Description;
        //for news details
        RecipelistAdapater( int ID, String JobTitle,String Description) {
            this.ID = ID;
            this.RecipeTitle = JobTitle;
            this.Description = Description;
        }

}
