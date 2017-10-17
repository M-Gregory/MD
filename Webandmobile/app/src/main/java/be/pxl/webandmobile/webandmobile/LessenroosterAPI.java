package be.pxl.webandmobile.webandmobile;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by René on 10/10/2017.
 */

public class LessenroosterAPI extends AsyncTask<Void, Void, String>{
    public void getX() {

    }


    @Override
    protected String doInBackground(Void... voids) {
        //haal het geselecteerde jaar op

        // Do some validation here

        /* try {
            URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } */
        return null;
    }
}
