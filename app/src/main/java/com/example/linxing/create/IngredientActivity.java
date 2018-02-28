package com.example.linxing.create;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by jiana on 2018/2/27.
 */

public class IngredientActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient);

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
}
