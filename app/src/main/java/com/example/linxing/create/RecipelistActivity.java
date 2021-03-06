package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RecipelistActivity extends AppCompatActivity implements RecipeJsonData.OnDataAvailable {

    private static final String TAG = "RecipelistActivity";

    private List<RecipeItem> recipeList = new ArrayList<>();
    private ListView mListView;
    private Toolbar mToolbar;
    private RecipeListViewAdapter adapter;
    private String[] ingredients;
    private FirebaseAuth myAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private UserProfile userInformation;
    private Spinner spinnerSort;
    private Comparators myComparator;
    private double dailyCalories;
    private String protein;
    private String fat;
    private String carbs;
    private int recipe_calories;
    private Spinner spinnerFilter;
    private StringBuilder result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        myComparator = new Comparators();
        mListView = findViewById(R.id.recilist);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        spinnerFilter = (Spinner) findViewById(R.id.filter);
        setSupportActionBar(mToolbar);
        Bundle b = getIntent().getExtras();
        spinnerSort = findViewById(R.id.sort);
        if (b != null) {
            ingredients = b.getStringArray("ingredientName");
            dailyCalories = b.getDouble("calories");


        }

        adapter = new RecipeListViewAdapter(this, recipeList, mListView, dailyCalories);
        mListView.setAdapter(adapter);
        result = new StringBuilder();
        for (String s: ingredients) {
            result.append(s).append(",");
        }

        Log.d(TAG, "onCreate: isssssss: " + result.toString());
        loadData(result.toString());


        // set return button
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),IngredientActivity.class));
                }
            });
        }
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recipeList = adapter.getmRecipeList();
                switch (position) {
                    case 0:
                        Collections.sort(recipeList, myComparator.new UsedIngredientComparator());
                        break;
                    case 1:
                        Collections.sort(recipeList, myComparator.new MissedIngredientComparator());
                        break;
                    case 2:
                        Collections.sort(recipeList, myComparator.new CaloriesComparator());
                        break;
                    default:;
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        // set return button
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        loadData(result.toString(), "american");
                        break;
                    case 2:
                        loadData(result.toString(), "chinese");
                        break;
                    case 3:
                        loadData(result.toString(), "korean");
                        break;
                    default:
                        loadData(result.toString());
                }
                adapter.notifyDataSetChanged();
            }

        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecipeItem recipe = (RecipeItem) adapterView.getItemAtPosition(i);

                int id = recipe.getID();
                protein = recipe.getProtein();
                fat = recipe.getFat();
                carbs = recipe.getCarbs();
                recipe_calories = recipe.getCalories();
                Bundle bundle = new Bundle();
                bundle.putInt("id",id);
                bundle.putStringArray("ingredientName", ingredients);
                bundle.putDouble("calories", dailyCalories);
                bundle.putInt("recipe_calories",recipe_calories);
                bundle.putString("protein",protein);
                bundle.putString("fat",fat);
                bundle.putString("carbs",carbs);
                Intent intent = new Intent();
                intent.setClass(RecipelistActivity.this, DetailRecipeActivity.class);
                intent.putExtras(bundle);

                Log.d(TAG, "onClick: ingredient id is: " + id);
                finish();
                startActivity(intent);

            }
        });

    }



    private View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    @Override
    public void onDataAvailable(List<RecipeItem> data, RecipeDownloadStatus status) {
        if (status == RecipeDownloadStatus.OK) {
            adapter.loadNewData(data);
            Log.d(TAG, "onDataAvailable: data is" + data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable: failed with status" + status );
        }
    }


    private void loadData(String... s) {
        RecipeJsonData recipeJsonData = new RecipeJsonData(this, "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex", true);
        recipeJsonData.execute(s);
    }
}
