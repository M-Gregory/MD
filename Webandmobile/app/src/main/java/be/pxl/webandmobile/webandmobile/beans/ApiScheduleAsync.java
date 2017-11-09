package be.pxl.webandmobile.webandmobile.beans;

import android.content.Context;
import android.widget.EditText;
import android.widget.ProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.pxl.webandmobile.webandmobile.Schedule;

/**
 * Created by René on 23/10/2017.
 */

//TODO: verwerken JSON data
public class ApiScheduleAsync extends ApiBaseClassAsync {
    private String className;
    private EditText[][] courses;

    public ApiScheduleAsync(Context context, ProgressBar progressBar, EditText[][] courses, String className) {
        super(context, progressBar);
        this.className = className;
        this.courses = courses;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String passedString) {
        super.onPostExecute(passedString);

        //get les, leerkracht en lokaal
        List<String> olodList = new ArrayList<String>();

        ApiBaseClassAsync api = new ApiGetClassData(super.getContext(), null, courses);

        try {
            JSONArray jsonArray = new JSONArray(passedString);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject classInfo = jsonArray.getJSONObject(i);
                String tempOlod = classInfo.getString("code_olod");
                olodList.add(tempOlod);
            }

            for(String olod: olodList) {
                api.execute("http://data.pxl.be/roosters/v1/klassen/" + className + "/vakken/" + olod);
            }
        } catch (JSONException e) {
            //TODO: add alert
        }
    }
}
