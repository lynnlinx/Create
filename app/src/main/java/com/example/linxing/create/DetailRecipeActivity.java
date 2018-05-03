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
import java.util.List;

/**
 * Created by jiana on 2018/2/27.
 */

public class DetailRecipeActivity extends AppCompatActivity implements RecipeDetailData.OnDataAvailable {
    private static final String TAG = "DetailRecipeActivity";
    private List<RecipeDetailItem> recipeList = new ArrayList<>();
    private ListView mListView;
    private ArrayAdapter adapter;
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
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,recipeList);
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
