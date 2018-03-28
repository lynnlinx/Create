package com.example.linxing.create;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
                view.setOnClickListener(new View.OnClickListener() {
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
            getSupportActionBar().setIcon(R.drawable.ic_barcode);

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
        map.put("title", "Chicken Spaghetti");
        map.put("info_nutrition", "Protein:37g    Carb:20g    Fat:24g");
        map.put("image", R.drawable.spaghetti);
        map.put("info_ingredient", "Cheese, chicken...");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Chicken Casseroles");
        map.put("info_nutrition", "Protein:41g    Carb:23g    Fat:30g");
        map.put("image", R.drawable.casseroles);
        map.put("info_ingredient", "Cheese, chicken...");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Creamy Quinoa");
        map.put("info_nutrition", "Protein:36g    Carb:21g    Fat:39g");
        map.put("image", R.drawable.quinoa);
        map.put("info_ingredient", "Cheese, chicken...");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Lasagne");
        map.put("info_nutrition", "Protein:36g    Carb:20g    Fat:55g");
        map.put("image", R.drawable.lasagne);
        map.put("info_ingredient", "Cheese, chicken...");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "Enchilada");
        map.put("info_nutrition", "Protein:28g    Carb:32g    Fat:42g");
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

    public void buGet(View view) {

        String url="https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" +
                "findByIngredients?fillIngredients=false&ingredients=apples%2Cflour%2Csugar&limitLicense=false&number=5&ranking=1'";
                //etCity.getText().toString() ;



        new MyAsyncTaskgetNews().execute(url);

    }
    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = ConvertInputToStringNoChange(in);
                    //send to display data
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {
                JSONObject json= new JSONObject(progress[0]);

                JSONObject query=json.getJSONObject("query");
                JSONObject results=query.getJSONObject("results");
                JSONObject channel=results.getJSONObject("channel");
                JSONObject astronomy=channel.getJSONObject("astronomy");
                String sunset=astronomy.getString("sunset");
                String sunrise=astronomy.getString("sunrise");

                //display response data
                Toast.makeText(getApplicationContext(),"sunset:"+ sunset + ",sunrise:"+ sunrise,Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String  result2){


        }

    }
    // this method convert any stream to string
    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }

}
