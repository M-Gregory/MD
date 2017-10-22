package be.pxl.webandmobile.webandmobile.beans;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 11400136 on 22/10/2017.
 */

public abstract class ApiBaseClassAsync extends AsyncTask<String, Void, JSONObject> {//AsyncTask<parameter, progress variable used for updating progressbar (integer/float/...), return type>
    private Context context;
    private ProgressBar progressBar;

    //Constructor:
    public ApiBaseClassAsync(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    //Async task:
    @Override
    protected JSONObject doInBackground(String... urls) {
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

        JSONObject object = null;

        //Json-ify:
        try {
            object = new JSONObject(stringBuffer.toString());
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
    protected void onPostExecute(JSONObject jsonObject) {
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
