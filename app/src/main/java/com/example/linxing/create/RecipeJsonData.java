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
 * Created by linxing on 5/2/18.
 */

public class RecipeJsonData extends AsyncTask<String, Void, List<RecipeItem>> implements GetRecipeRawData.OnDownloadComplete  {
    private static final String TAG = "RecipeJsonData";
    private List<RecipeItem> mRecipeItems = null;
    private String mBaseURL;
    private boolean mMatchAll;

    private final RecipeJsonData.OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(List<RecipeItem> data, RecipeDownloadStatus status);
    }

    public RecipeJsonData(RecipeJsonData.OnDataAvailable callBack, String baseURL, boolean matchAll) {
        mBaseURL = baseURL;
        mMatchAll = matchAll;
        mCallBack = callBack;
    }

    void executeOnSameThread(String searchCriteria) {
        Log.d(TAG, "executeOnSameThread: starts");
        runningOnSameThread = true;
        String destinationUri = createUri(searchCriteria, mMatchAll);
        GetRecipeRawData getRecipeRawData = new GetRecipeRawData(this);
        getRecipeRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread: ends");
    }

    private String createUri(String searchCriteria, boolean matchAll) {
        Log.d(TAG, "createUri: starts");
        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("offset", "0")
                .appendQueryParameter("number", "10")
                .appendQueryParameter("limitLicense", "false")
                .appendQueryParameter("includeIngredients", searchCriteria)
                .appendQueryParameter("minCarbs", "0")
                .build().toString();
    }

    private String createFilterUri(String searchCriteria, String category, boolean matchAll) {
        Log.d(TAG, "createFilterUri: starts");
        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("offset", "0")
                .appendQueryParameter("number", "10")
                .appendQueryParameter("limitLicense", "false")
                .appendQueryParameter("includeIngredients", searchCriteria)
                .appendQueryParameter("minCarbs", "0")
                .appendQueryParameter("cuisine", category)
                .build().toString();
    }


    @Override
    public void onDownloadComplete(String data, RecipeDownloadStatus status) {
        Log.d(TAG, "onDownloadComplete stars.Status = " + status);

        if (status == RecipeDownloadStatus.OK) {
            mRecipeItems = new ArrayList<>();
            try {
                JSONObject jsonObject =  new JSONObject(data);
                Log.d(TAG, "onDownloadComplete: " + data);
                JSONArray itemsArray = jsonObject.getJSONArray("results");
                Log.d(TAG, "onDownloadComplete: item is:"+ itemsArray);

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonRecipe = itemsArray.getJSONObject(i);

                    int id = jsonRecipe.getInt("id");
                    String title = jsonRecipe.getString("title");
                    String imageURL = jsonRecipe.getString("image");
                    String imageType = jsonRecipe.getString("imageType");
                    int usedIngredientCount = jsonRecipe.getInt("usedIngredientCount");
                    int missedIngredientCount = jsonRecipe.getInt("missedIngredientCount");
                    int calories = jsonRecipe.getInt("calories");
                    String protein = jsonRecipe.getString("protein");
                    String fat = jsonRecipe.getString("fat");
                    String carbs = jsonRecipe.getString("carbs");

                    RecipeItem recipeItem = new RecipeItem(id, title, usedIngredientCount, missedIngredientCount, imageURL, imageType, calories, protein, fat, carbs);
                    mRecipeItems.add(recipeItem);

                    Log.d(TAG, "onDownloadComplete" + recipeItem.toString());
                }
            } catch (JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data" + jsone.getMessage() );
                status = RecipeDownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if (runningOnSameThread && mCallBack != null) {
            // now inform the caller that the processing is done-possibly returning null if error
            mCallBack.onDataAvailable(mRecipeItems, status);
        }
        Log.d(TAG, "onDownloadComplete: ends");
    }

    @Override
    protected void onPostExecute(List<RecipeItem> ingredients) {
        Log.d(TAG, "onPostExecute: starts");

        if (mCallBack != null) {
            mCallBack.onDataAvailable(mRecipeItems, RecipeDownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: ends");
    }


    @Override
    protected List<RecipeItem> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        String destinationUri = "";
        if (params.length == 1) {
            destinationUri = createUri(params[0], mMatchAll);
        } else if (params.length == 2) {
            destinationUri = createFilterUri(params[0], params[1], mMatchAll);
        }

        GetRecipeRawData getRecipeRawData = new GetRecipeRawData(this);
        getRecipeRawData.runInSameThread(destinationUri);
        Log.d(TAG, "doInBackground: ends");
        return null;
    }


    private String getImageURL(int id) {
        StringBuilder mBase = new StringBuilder();
        mBase.append("https://spoonacular.com/recipeImages/").append(String.valueOf(id))
        .append("-90x90.jpg");
        return mBase.toString();
    }
}
