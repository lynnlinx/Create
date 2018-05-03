package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiana on 2018/2/27.
 */

public class DetailRecipeActivity extends AppCompatActivity implements RecipeDetailData.OnDataAvailable {
    private static final String TAG = "DetailRecipeActivity";
    private List<String> recipeList = new ArrayList<>();
    private ListView mListView;
    private ImageView mImageView;
    private TextView mTextView;
    private ArrayAdapter adapter;
    private String[] ingredients;
    private double dailyCalories;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailrecipe);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            ingredients = b.getStringArray("ingredientName");
            dailyCalories = b.getDouble("calories");
        }

        mListView = findViewById(R.id.detail_listview);
        mImageView = findViewById(R.id.recipe_image);
        mTextView = findViewById(R.id.recipe_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

//        adapter = new DetailRecipeAdapter(this,recipeList,mListView);
//        mListView.setAdapter(adapter);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,recipeList);
        mListView.setAdapter(adapter);
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
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("ingredientName", ingredients);
                    bundle.putDouble("calories", dailyCalories);
                    Intent intent = new Intent();
                    intent.setClass(DetailRecipeActivity.this, RecipelistActivity.class);
                    intent.putExtras(bundle);
                    finish();
                    startActivity(intent);

                    //startActivity(new Intent(getApplicationContext(),RecipelistActivity.class));
                }
            });
        }
    }

    public void onDataAvailable(List<RecipeDetailItem> data, RecipeDownloadStatus status) {
        if (status == RecipeDownloadStatus.OK) {
            //adapter.loadNewData(data);
            Log.d(TAG, "onDataAvailable: nooooooooo: "+ data.get(0).toString());
            Picasso.with(mImageView.getContext()).load(data.get(0).getImage())
                    .error(R.drawable.ic_filter)
                    .placeholder(R.drawable.ic_filter)
                    .into(mImageView);

            mTextView.setText(data.get(0).getTitle());
            recipeList.add(data.get(0).toString());
            adapter.notifyDataSetChanged();
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
