package com.example.linxing.create;

import java.util.List;

/**
 * Created by linxing on 3/16/18.
 */

class IngredientJsonData implements GetRawData.OnDownloadComplete {

    private static final String TAG = "IngredientJsonData";
    private List<Ingredient> mIngredient = null;
    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

    }
}
