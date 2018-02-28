package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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

        ListView listView = (ListView)findViewById(R.id.recipe_list);

        SimpleAdapter adapter = new SimpleAdapter(this, getData(),
                R.layout.ingredient_list_item,
                new String[] { "title", "info", "image" },
                new int[] { R.id.title, R.id.info, R.id.image });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        finish();
        startActivity(new Intent(this, RecipelistActivity.class));
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map;

        map = new HashMap<String, Object>();
        map.put("title", "Title1");
        map.put("info", "infoinfo1");
        map.put("image", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Title2");
        map.put("info", "infoinfo2");
        map.put("image", R.drawable.ic_filter);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Title3");
        map.put("info", "infoinfo3");
        map.put("image", R.drawable.ic_filter);
        list.add(map);

        return list;
    }
}
