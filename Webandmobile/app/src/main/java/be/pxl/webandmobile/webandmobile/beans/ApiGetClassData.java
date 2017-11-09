package be.pxl.webandmobile.webandmobile.beans;

import android.content.Context;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by René on 07/11/2017.
 */

public class ApiGetClassData extends ApiBaseClassAsync {
    private EditText[][] courses;

    public ApiGetClassData(Context context, ProgressBar progressBar, EditText[][] courses) {
        super(context, progressBar);

        this.courses = courses;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String passedString) {
        super.onPostExecute(passedString);

        try {
            JSONArray jsonArray = new JSONArray(passedString);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject classInfo = jsonArray.getJSONObject(i);
                String tempdate = classInfo.getString("datum");
            }
        } catch (JSONException e) {
            //TODO: add alert
        }
    }
}
