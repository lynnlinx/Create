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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiana on 2018/2/27.
 */

public class DetailRecipeActivity extends AppCompatActivity implements RecipeDetailData.OnDataAvailable,  View.OnClickListener{
    private static final String TAG = "DetailRecipeActivity";

    private List<String> ingreList = new ArrayList<>();
    private List<String> instruclist = new ArrayList<>();
    private ListView mListView;
    private ListView instruction_view;
    private ImageView mImageView;
    private TextView mTextView;
    private TextView nutrientsView;
    private TextView otherView;
    private ArrayAdapter adapter;
    private ArrayAdapter ingre_adapter;
    private String[] ingredients;
    private StringBuilder instructions = new StringBuilder();
    private String protein;
    private String fat;
    private String carbs;
    private int recipe_calories;
    private TextView buttonRemove;
    private TextView buttonList;


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
            protein = b.getString("protein");
            fat = b.getString("fat");
            carbs = b.getString("carbs");
            recipe_calories = b.getInt("recipe_calories");
        }
        buttonList = (TextView) findViewById(R.id.btn_shoppinglist);
        buttonList.setOnClickListener(this);
        buttonRemove = (TextView) findViewById(R.id.btn_remove);
        buttonRemove.setOnClickListener(this);
        mListView = findViewById(R.id.detail_listview);
        instruction_view = findViewById(R.id.instructions_listview);
        mImageView = findViewById(R.id.recipe_image);
        mTextView = findViewById(R.id.recipe_name);
        nutrientsView = findViewById(R.id.nutrients);
        otherView = findViewById(R.id.others);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

//        adapter = new DetailRecipeAdapter(this,recipeList,mListView);
//        mListView.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ingreList);
        mListView.setAdapter(adapter);

        ingre_adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,instruclist);
        instruction_view.setAdapter(ingre_adapter);

        id = b.getInt("id");

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
            Log.d(TAG, "onDataAvailable: nooooooooo: "+ data.get(0));
            Picasso.with(mImageView.getContext()).load(data.get(0).getImage())
                    .error(R.drawable.ic_filter)
                    .placeholder(R.drawable.ic_filter)
                    .into(mImageView);

            mTextView.setText(data.get(0).getTitle());
            String nutrientString = "Calories: "+recipe_calories+"  Protein: "+protein+"  Carbs: "+carbs;
            nutrientsView.setText(nutrientString);
            String otherString = "Servings: "+data.get(0).getServings()+"           Time: "+data.get(0).getMinutes()+" minutes";
            otherView.setText(otherString);
            String string = data.get(0).toString();
            int index = string.indexOf("instructions:");
            ingreList.add(string.substring(0,index));
            instruclist.add(string.substring(index+13));
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
    @Override
    public void onClick(View v) {
        if (v== buttonList) {
            Toast.makeText(this, "Be coming soon", Toast.LENGTH_SHORT).show();
        }
        if (v== buttonRemove) {
            Toast.makeText(this, "Be coming soon", Toast.LENGTH_SHORT).show();
        }
    }
}
