package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by jiana on 2018/2/27.
 */

public class IngredientActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSearch;
    private static final String TAG = "IngredientActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient);

        buttonSearch = (Button) findViewById(R.id.btn_search_recipe);
        buttonSearch.setOnClickListener(this);


        String[] fake = new String[5];
        for (int i = 0; i < 5; i++) {
            fake[i] = "123";
        }
        // Set the listview to display all questions
        ListView listView = (ListView)findViewById(R.id.recipe_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                fake);

        listView.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        finish();
        startActivity(new Intent(this, RecipelistActivity.class));
    }
}
