package com.example.linxing.create;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by linxing on 5/2/18.
 */

enum RecipeDownloadStatus { IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK }

public class GetRecipeRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRecipeRawData";

    private RecipeDownloadStatus mDownloadStatus;
    private final GetRecipeRawData.OnDownloadComplete mCallback;

    interface OnDownloadComplete {
        void onDownloadComplete(String data, RecipeDownloadStatus status);
    }

    public GetRecipeRawData(GetRecipeRawData.OnDownloadComplete callback) {
        this.mDownloadStatus = RecipeDownloadStatus.IDLE;
        mCallback = callback;
    }

    void runInSameThread(String s) {
        Log.d(TAG, "runInSameThread: starts");

        //onPostExecute(doInBackground(s));
        if (mCallback != null) {
            String result = doInBackground(s);
            mCallback.onDownloadComplete(result, mDownloadStatus);
        }
        Log.d(TAG, "runInSameThread: ends");
    }


    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: parameter == " + s);
        if (mCallback != null) {
            mCallback.onDownloadComplete(s, mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            mDownloadStatus = RecipeDownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            mDownloadStatus = RecipeDownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Mashape-Host", BuildConfig.SPOONACULAR_API_ID);
            connection.setRequestProperty("X-Mashape-Key", BuildConfig.SPOONACULAR_API_KEY);
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code was" + response);

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while (null != (line = reader.readLine())) {
                result.append(line).append("\n");
            }

            mDownloadStatus = RecipeDownloadStatus.OK;
            return result.toString();

        } catch(MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL" + e.getMessage() );
        } catch(IOException e) {
            Log.e(TAG, "doInBackground: IO Exception reading data" + e.getMessage() );
        } catch(SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exceptions. Need Permission?" + e.getMessage() );
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream" + e.getMessage() );
                }

            }
        }

        mDownloadStatus = RecipeDownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
