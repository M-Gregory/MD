package be.pxl.webandmobile.webandmobile.beans;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 11400136 on 22/10/2017.
 */

public class ApiRoutesAsync extends ApiBaseClassAsync {
    private TextView textView;

    //Constructor:
    public ApiRoutesAsync(Context context, ProgressBar progressBar, TextView textView) {
        super(context, progressBar);
        this.textView = textView;
    }

    //Before:
    @Override
    protected void onPreExecute() {
        //textView.setText("");
        //textView.setVisibility(TextView.GONE);

        super.onPreExecute();
    }

    //After:
    @Override
    protected void onPostExecute(String passedString) {//'result' ciomes out of doInBackground(...) (look at ApiBaseClassAsync!)
        JSONObject jsonObject = null;
        ArrayList<ApiSetupClassOne> list = new ArrayList<>();

        try {
            jsonObject = new JSONObject(passedString);

            //should return multiple objects:
            JSONArray jsonArray = jsonObject.getJSONArray("reiswegen");
            StringBuilder sb = new StringBuilder();
            //sb.append("Gevonden ritten:\n\n");
            //sb.append("");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                //sb.append("Aankomst datum: " + jsonObject.getString("endDate"));
                //sb.append("Vertrek: " + jsonObject.getString("startTime"));
                //sb.append("\n");
                //sb.append("Duur: " + jsonObject.getString("duration"));
                //sb.append("\n");
                //sb.append("Aandomst: " + jsonObject.getString("endTime"));
                //sb.append("\n");
                //sb.append("Overstappen: " + jsonObject.getString("overstappen"));
                //sb.append("\n");
                //sb.append("Omleiding: " + jsonObject.getString("heeftOmleidingen"));


                sb.append(jsonObject.getString("startTime"));
                sb.append("\n");
                sb.append(jsonObject.getString("endTime"));
                sb.append("      \n\n");
            }

            textView.setText(sb.toString().trim());

            if (jsonArray.length() <= 0) {
                textView.setText("Geen reis met de bus mogelijk.");
            }

        } catch (JSONException e) {
            list = null;
            textView.setText("Error parsing json...");
        }

        //textView.setVisibility(TextView.VISIBLE);
        super.onPostExecute(passedString);
    }
}
