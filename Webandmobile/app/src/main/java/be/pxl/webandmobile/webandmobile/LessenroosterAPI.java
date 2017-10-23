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
    public String[] getClasses(String jaar) {
        //haalt alle klassen op van het gekozen jaar/richting
        //1TIN, 2TIN, 3AON, 3SNB of 3 SWM
        




        String[] test = {"", ""};

        return test;
    }

    public void getSpecClasses() {
        //haalt de info op vd klas, naam, lector, code
        //bv call op http://data.pxl.be/roosters/v1/klassen/3AOND/vakken
    }

    public void getClassDetails() {
        //haalt details vd klas op, uren etc
        //call op http://data.pxl.be/roosters/v1/klassen/3AOND/vakken/4547PR
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
