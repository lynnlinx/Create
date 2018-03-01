package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class RecipelistActivity extends AppCompatActivity {
    //adapter class
    ArrayList<RecipelistAdapater> listnewsData = new ArrayList<RecipelistAdapater>();
    MyCustomAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        ListView recilist = (ListView)findViewById(R.id.recilist);

        //add data and view it
        listnewsData.add(new RecipelistAdapater(R.drawable.spaghetti,"chicken spaghetti",
                "Needs: tomatoes,spaghetti..."));
        listnewsData.add(new RecipelistAdapater(R.drawable.casseroles,"chicken casseroles",
                "Needs: chicken,cheese..."));
        listnewsData.add(new RecipelistAdapater(R.drawable.quinoa,"creamy quinoa",
                "Needs: "));
        listnewsData.add(new RecipelistAdapater(R.drawable.lasagne,"lasagne",
                "Needs:"));
        listnewsData.add(new RecipelistAdapater(R.drawable.enchilada,"enchilada",
                "Needs:"));

        myadapter=new MyCustomAdapter(listnewsData);
        recilist.setAdapter(myadapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),IngredientActivity.class));
                }
            });
        }


    }

    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<RecipelistAdapater> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<RecipelistAdapater> listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_recipe, null);

            final RecipelistAdapater s = listnewsDataAdpater.get(position);

            ImageView ivImage=(ImageView) myView.findViewById(R.id.ivImage);
            ivImage.setImageResource(s.ID);

            TextView relistDesc=( TextView)myView.findViewById(R.id.reciDesc);
            relistDesc.setText(s.Description);

            TextView recititle=( TextView)myView.findViewById(R.id.reciTitle);
            recititle.setText(s.RecipeTitle);

            return myView;
        }

    }
}
