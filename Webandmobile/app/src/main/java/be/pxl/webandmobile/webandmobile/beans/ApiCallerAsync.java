package be.pxl.webandmobile.webandmobile.beans;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
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

public class ApiCallerAsync extends AsyncTask<String, Void, JSONObject> {//AsyncTask<parameter, progress variable used for updating progressbar (integer/float/...), return type>
    private Context context;
    private ProgressBar progressBar;
    private TextView textView;

    public ApiCallerAsync(Context context, ProgressBar progressBar, TextView textView) {
        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
    }

    //async task
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

        //make it a json:
        try {
            object = new JSONObject(stringBuffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    //before start
    @Override
    protected void onPreExecute() {
        textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    //when done
    @Override
    protected void onPostExecute(JSONObject jsonObject) {//'result' ciomes out of doInBackground(...)
        textView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        String result = "";
        try {
            //should return multiple objects:
            JSONArray jsonArray = jsonObject.getJSONArray("locations");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                result += "locatie:" + jsonObject.getString("idString") + "\nx coordinaat: " + jsonObject.getString("xCoordinaat") + "\ny coordinaat: " + jsonObject.getString("yCoordinaat") + "\n\n";
            }
        } catch (JSONException e) {
            result = "unparseable json string.";
        }

        textView.setText(result);
    }


//    @Override
//    protected void onProgressUpdate(Void... values) {
//        super.onProgressUpdate(values);
//    }

}
