package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //adapter class
    ArrayList<RecipelistAdapater> listnewsData = new ArrayList<RecipelistAdapater>();
    MyCustomAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView recilist = (ListView)findViewById(R.id.recilist);

        //add data and view it
        listnewsData.add(new RecipelistAdapater(1,"developer","develop apps"));
        listnewsData.add(new RecipelistAdapater(2,"tester","develop apps"));

        myadapter=new MyCustomAdapter(listnewsData);
        recilist.setAdapter(myadapter);
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

            TextView relistID=( TextView)myView.findViewById(R.id.relistID);
            relistID.setText(s.ID);

            TextView relistDesc=( TextView)myView.findViewById(R.id.reciDesc);
            relistDesc.setText(s.Description);

            TextView recititle=( TextView)myView.findViewById(R.id.reciTitle);
            recititle.setText(s.RecipeTitle);

            return myView;
        }

    }
}
