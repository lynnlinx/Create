package com.example.linxing.create;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class MyAsyncTaskgetRecipe extends AsyncTask<String, String, String> {
        private static final String TAG = "RecipeJsonData";

        @Override
        protected void onPreExecute() {
            //before works
        }

        @Override
        protected String doInBackground(String... params) {
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

            } catch (Exception ex) {
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {

            try {
                JSONObject json = new JSONObject(progress[0]);
                JSONArray itemsArray = json.getJSONArray("foods");
                Log.d(TAG, "In REcipelist");
                Log.d(TAG, "IN RECIPELIST onDownloadComplete: item is:" + itemsArray);

//                JSONObject query=json.getJSONObject("query");
//                JSONObject results=query.getJSONObject("results");
//                JSONObject channel=results.getJSONObject("channel");
//                JSONObject astronomy=channel.getJSONObject("astronomy");
//                String sunset=astronomy.getString("sunset");
//                String sunrise=astronomy.getString("sunrise");

                //display response data
                //Toast.makeText(getApplicationContext(),"sunset:"+ sunset + ",sunrise:"+ sunrise,Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String result2) {


        }

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
