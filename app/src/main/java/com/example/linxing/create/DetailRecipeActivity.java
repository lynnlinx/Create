package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jiana on 2018/2/27.
 */

public class DetailRecipeActivity extends AppCompatActivity implements RecipeDetailData.OnDataAvailable {
    private static final String TAG = "DetailRecipeActivity";
    private List<String> instructions = new ArrayList<>();
    private List<String> ingredients = new ArrayList<>();
    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailrecipe);
        mListView = findViewById(R.id.detail_listview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

//        adapter = new DetailRecipeAdapter(this,recipeList,mListView);
//        mListView.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,instructions);
        mListView.setAdapter(adapter);
        Bundle b = getIntent().getExtras();
        id = b.getInt("id"); //???

        Log.d(TAG, "DetailRecipe onCreate: is: " + id);
        loadData(""+id);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),RecipelistActivity.class));
                }
            });
        }
    }

    public void onDataAvailable(List<RecipeDetailItem> data, RecipeDownloadStatus status) {
        if (status == RecipeDownloadStatus.OK) {
            //adapter.loadNewData(data);
            RecipeDetailItem unhandled = data.get(0);
            ArrayList<String> steps = unhandled.getInstructions();
            for(int i=0;i<steps.size();i++) {
                String step_string = steps.get(i);
                String combine = i+1+". "+step_string+"\n";
                instructions.add(combine);
            }

            ArrayList<RecipeIngredient> ingre = unhandled.getRecipeIngredients();
            String ingre_string = ingre.toString();

            Log.d(TAG, "onDataAvailable: instructions are" + instructions);
            ingredients.add(ingre_string);
            adapter.notifyDataSetChanged();
            Log.d(TAG, "onDataAvailable: data is" + data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable: failed with status" + status );
        }
    }



    private void loadData(String s) {
        RecipeDetailData recipeDetailData = new RecipeDetailData(this, "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes", true);
        recipeDetailData.execute(s);
    }
}
