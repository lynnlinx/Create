package com.example.linxing.create;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailData extends AsyncTask<String, Void, List<RecipeDetailItem>> implements GetRecipeRawData.OnDownloadComplete {
    private static final String TAG = "RecipeDetailJsonData";
    private List<RecipeDetailItem> mRecipeItems = null;
    private String mBaseURL;
    private boolean mMatchAll;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(List<RecipeDetailItem> data, RecipeDownloadStatus status);
    }
    public RecipeDetailData(OnDataAvailable callBack, String baseURL, boolean matchAll) {
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
        String tmp =  Uri.parse(mBaseURL).buildUpon()
                .appendEncodedPath(searchCriteria)
                .appendEncodedPath("information")
                .build().toString();
        return tmp;
    }

    @Override
    public void onDownloadComplete(String data, RecipeDownloadStatus status) {
        Log.d(TAG, "onDownloadComplete stars.Status = " + status);

        if (status == RecipeDownloadStatus.OK) {
            mRecipeItems = new ArrayList<>();
            try {
                JSONObject jsonObject =  new JSONObject(data);
                Log.d(TAG, "onDownloadComplete: ????????" + data);
                //JSONArray itemsArray = jsonObject.getJSONArray("results");
                //Log.d(TAG, "onDownloadComplete: item is:"+ itemsArray);

                //for (int i = 0; i < itemsArray.length(); i++) {
                    //JSONObject jsonRecipe = itemsArray.getJSONObject(i);


                String title = jsonObject.getString("title");
                String imageURL = jsonObject.getString("image");
                String imageType = jsonObject.getString("imageType");
                int servings = jsonObject.getInt("servings");
                int minutes = jsonObject.getInt("readyInMinutes");
                String dishType = jsonObject.getString("dishTypes");
                String diet = jsonObject.getString("diets");
                JSONArray instructions = jsonObject.getJSONArray("analyzedInstructions");
                JSONArray ingredients = jsonObject.getJSONArray("extendedIngredients");
                JSONObject instructionOpen = instructions.getJSONObject(0);
                JSONArray instructionSteps = instructionOpen.getJSONArray("steps");
                ArrayList<String> steps= new ArrayList<>();
                ArrayList<RecipeIngredient> recipe_ingre = new ArrayList<>();

                for(int i=0;i<instructionSteps.length();i++) {
                    JSONObject step = instructionSteps.getJSONObject(i);
                    String eachStep = step.getString("step");
                    steps.add(eachStep);
                }
                Log.d("in RecipeDetail Data",""+steps);

                for(int i=0;i<ingredients.length();i++) {
                    JSONObject eachIngre = ingredients.getJSONObject(i);
                    String ingre_name = eachIngre.getString("name");
                    String unit = eachIngre.getString("unit");
                    int amount = eachIngre.getInt("amount");
                    recipe_ingre.add(new RecipeIngredient(amount,ingre_name,unit));
                }

                RecipeDetailItem recipeItem = new RecipeDetailItem(title, dishType, diet, imageURL,
                        imageType, minutes, servings, recipe_ingre, steps);
                mRecipeItems.add(recipeItem);

                Log.d(TAG, "onDownloadComplete" + recipeItem.toString());

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
    protected void onPostExecute(List<RecipeDetailItem> ingredients) {
        Log.d(TAG, "onPostExecute: starts");

        if (mCallBack != null) {
            mCallBack.onDataAvailable(mRecipeItems, RecipeDownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected List<RecipeDetailItem> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        String destinationUri = createUri(params[0], mMatchAll);

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
