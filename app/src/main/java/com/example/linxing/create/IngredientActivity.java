package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by jiana on 2018/2/27.
 */

public class IngredientActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSearch;
    private TextView buttonDelete;
    private static final String TAG = "IngredientActivity";
    private List<Map<String, Object>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient);

        SideslipListView listView = findViewById(R.id.recipe_list);
        buttonSearch = (Button) findViewById(R.id.btn_search_recipe);
        buttonSearch.setOnClickListener(this);
        mData = getData();

        SimpleAdapter adapter = new SimpleAdapter(this, mData,
                R.layout.ingredient_list_item,
                new String[] { "title", "info", "image" },
                new int[] { R.id.title, R.id.info, R.id.image }) {

            @Override
            public View getView (int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                buttonDelete = (TextView) view.findViewById(R.id.txtv_delete);
                if (null == view) {
                    view = View.inflate(IngredientActivity.this, R.layout.ingredient, null);
                }
                final int pos = position;
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mData.remove(pos);
                        notifyDataSetChanged();
                    }
                });
                return view;
            }

        };

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        if (v == buttonSearch) {
            finish();
            startActivity(new Intent(this, RecipelistActivity.class));
        }
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map;

        map = new HashMap<String, Object>();
        map.put("title", "Chicken Breast");
        map.put("info", "Protein:37g    Carb:0g    Fat:4g");
        map.put("image", R.mipmap.ic_chickenbreast);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Broccoli");
        map.put("info", "Protein:1g    Carb:3g    Fat:0g");
        map.put("image", R.mipmap.ic_broccoli);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Cheese");
        map.put("info", "Protein:6g    Carb:1g    Fat:9g");
        map.put("image", R.mipmap.ic_cheese);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Egg");
        map.put("info", "Protein:6g    Carb:0g    Fat:5g");
        map.put("image", R.mipmap.ic_egg);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Milk");
        map.put("info", "Protein:8g    Carb:12g    Fat:2g");
        map.put("image", R.mipmap.ic_milk);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Noodle");
        map.put("info", "Protein:7g    Carb:38g    Fat:1g");
        map.put("image", R.mipmap.ic_noodle);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Onion");
        map.put("info", "Protein:1g    Carb:10g    Fat:0g");
        map.put("image", R.mipmap.ic_onion);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Tomato");
        map.put("info", "Protein:1g    Carb:5g    Fat:0g");
        map.put("image", R.mipmap.ic_tomato);
        list.add(map);

        return list;
    }

}
