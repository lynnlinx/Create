package com.example.linxing.create;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by linxing on 3/16/18.
 */

class IngredientJsonData extends AsyncTask<String, Void, List<Ingredient>> implements GetRawData.OnDownloadComplete {

    private static final String TAG = "IngredientJsonData";
    private List<Ingredient> mIngredient = null;
    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

    }

    @Override
    protected List<Ingredient> doInBackground(String... strings) {
        return null;
    }
}
