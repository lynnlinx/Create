package com.example.linxing.create;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxing on 3/16/18.
 */

class IngredientJsonData extends AsyncTask<String, Void, List<Ingredient>> implements GetRawData.OnDownloadComplete {

    private static final String TAG = "IngredientJsonData";
    private List<Ingredient> mIngredient = null;
    private String mBaseURL;
    private boolean mMatchAll;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(List<Ingredient> data, DownloadStatus status);
    }

    public IngredientJsonData(OnDataAvailable callBack, String baseURL, boolean matchAll) {
        mBaseURL = baseURL;
        mMatchAll = matchAll;
        mCallBack = callBack;
    }

    void executeOnSameThread(String searchCriteria) {
        Log.d(TAG, "executeOnSameThread: starts");
        runningOnSameThread = true;
        String destinationUri = createUri(searchCriteria, mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread: ends");
    }

    private String createUri(String searchCriteria, boolean matchAll) {
        Log.d(TAG, "createUri: starts");
        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("query", searchCriteria)
                .build().toString();
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete stars.Status = " + status);

        if (status == DownloadStatus.OK) {
            mIngredient = new ArrayList<>();
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("branded");
                Log.d(TAG, "onDownloadComplete: item is:"+ itemsArray);

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonIngredient = itemsArray.getJSONObject(i);
                    String food_name = jsonIngredient.getString("food_name");
                    JSONObject image = jsonIngredient.getJSONObject("photo");
                    String imageUrl = image.getString("thumb");
                    String serving_unit = jsonIngredient.getString("serving_unit");
                    String brand_name_item_name = jsonIngredient.getString("brand_name_item_name");
                    String nf_calories = jsonIngredient.getString("nf_calories");
                    String brand_name = jsonIngredient.getString("brand_name");

                    //String link = imageUrl.replaceFirst("_m.", "_b.");
                    
                    Ingredient ingredientObject = new Ingredient(food_name, imageUrl, serving_unit, brand_name_item_name, nf_calories, brand_name);
                    mIngredient.add(ingredientObject);

                    Log.d(TAG, "onDownloadComplete" + ingredientObject.toString());
                }
            } catch (JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data" + jsone.getMessage() );
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if (runningOnSameThread && mCallBack != null) {
            // now inform the caller that the processing is done-possibly returning null if error
            mCallBack.onDataAvailable(mIngredient, status);
        }
        Log.d(TAG, "onDownloadComplete: ends");
    }

    @Override
    protected void onPostExecute(List<Ingredient> ingredients) {
        Log.d(TAG, "onPostExecute: starts");

        if (mCallBack != null) {
            mCallBack.onDataAvailable(mIngredient, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: ends");
    }


    @Override
    protected List<Ingredient> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        String destinationUri = createUri(params[0], mMatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        Log.d(TAG, "doInBackground: ends");
        return null;
    }
}
