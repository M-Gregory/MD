package be.pxl.webandmobile.webandmobile.beans;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 11400136 on 22/10/2017.
 */

public class ApiCoordinatesAsync extends ApiBaseClassAsync {
    private ListView listView;

    //Constructor:
    public ApiCoordinatesAsync(Context context, ProgressBar progressBar, ListView listView) {
        super(context, progressBar);

        this.listView = listView;
    }

    //Before:
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listView.setVisibility(View.INVISIBLE);
    }

    //After:
    @Override
    protected void onPostExecute(JSONObject jsonObject) {//'result' ciomes out of doInBackground(...) (look at ApiBaseClassAsync!)
        super.onPostExecute(jsonObject);

        listView.setVisibility(View.VISIBLE);

        ArrayList<ApiSetupClassOne> list = new ArrayList<>();

        try {
            //should return multiple objects:
            JSONArray jsonArray = jsonObject.getJSONArray("locations");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                //result += "locatie:" + jsonObject.getString("idString") + "\nx coordinaat: " + jsonObject.getString("xCoordinaat") + "\ny coordinaat: " + jsonObject.getString("yCoordinaat") + "\n\n";
                list.add(new ApiSetupClassOne(jsonObject.getString("idString"), jsonObject.getInt("xCoordinaat"), jsonObject.getInt("yCoordinaat")));
            }
        } catch (JSONException e) {
            list = null;
        }

        //listView.setText(result);
        listView.setAdapter(new ArrayAdapter<ApiSetupClassOne>(super.getContext(), android.R.layout.simple_list_item_1, list));
    }
}
