package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecipelistActivity extends AppCompatActivity {

    private static final String TAG = "IngredientActivity";
    private List<Map<String, Object>> listnewsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        ListView recilist = (ListView)findViewById(R.id.recilist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listnewsData = getData();

        SimpleAdapter myadapter = new SimpleAdapter(this, listnewsData,
                R.layout.recipe_list_item,
                new String[] { "title", "info_nutrition", "image", "info_ingredient" },
                new int[] { R.id.title, R.id.info_nutrition, R.id.image, R.id.info_ingredient}) {

            @Override
            public View getView (int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                LinearLayout rl = (LinearLayout) findViewById(R.id.recipe_item);
                if (null == rl) {
                    view = View.inflate(RecipelistActivity.this, R.layout.recipe_list, null);
                }
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(new Intent(RecipelistActivity.this, DetailRecipeActivity.class));
                    }
                });
                return view;
            }
        };

        recilist.setAdapter(myadapter);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),IngredientActivity.class));
                }
            });
        }

    }




        private List<Map<String, Object>> getData() {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            Map<String, Object> map;

            map = new HashMap<String, Object>();
            map.put("title", "chicken spaghetti");
            map.put("info_nutrition", "Protein:37g    Carb:0g    Fat:4g");
            map.put("image", R.drawable.spaghetti);
            map.put("info_ingredient", "Cheese, chicken...");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("title", "chicken casseroles");
            map.put("info_nutrition", "Protein:1g    Carb:3g    Fat:0g");
            map.put("image", R.drawable.casseroles);
            map.put("info_ingredient", "Cheese, chicken...");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("title", "creamy quinoa");
            map.put("info_nutrition", "Protein:6g    Carb:1g    Fat:9g");
            map.put("image", R.drawable.quinoa);
            map.put("info_ingredient", "Cheese, chicken...");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("title", "lasagne");
            map.put("info_nutrition", "Protein:6g    Carb:0g    Fat:5g");
            map.put("image", R.drawable.lasagne);
            map.put("info_ingredient", "Cheese, chicken...");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("title", "enchilada");
            map.put("info_nutrition", "Protein:8g    Carb:12g    Fat:2g");
            map.put("image", R.drawable.enchilada);
            map.put("info_ingredient", "Cheese, chicken...");
            list.add(map);

            return list;
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

}
