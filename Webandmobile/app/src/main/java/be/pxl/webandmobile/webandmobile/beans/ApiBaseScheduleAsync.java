package be.pxl.webandmobile.webandmobile.beans;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by René on 23/10/2017.
 */

//Kan later waarschijnlijk gemerged worden met ApiBaseClassAsync
public abstract class ApiBaseScheduleAsync extends AsyncTask<String, Void, JSONArray> {
    private Context context;
    private ProgressBar progressBar;

    //Constructor:
    public ApiBaseScheduleAsync(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected JSONArray doInBackground(String... urls) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder stringBuffer = null;

        try {
            //url:
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();

            //read data streams:
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            stringBuffer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }

            //all data stored in stringBuffer
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (stringBuffer == null)
            return null;

        JSONArray object = null;

        //Json-ify:
        try {
            object = new JSONArray(stringBuffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    //Before:
    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    //After:
    @Override
    protected void onPostExecute(JSONArray jsonObject) {
        progressBar.setVisibility(View.GONE);
    }

    //Getters:
    public Context getContext() {
        return context;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
