package be.pxl.webandmobile.webandmobile.lessenrooster;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.pxl.webandmobile.webandmobile.beans.ApiBaseClassAsync;

/**
 * Created by René on 23/10/2017.
 */

//TODO: verwerken JSON data
public class ApiScheduleAsync extends ApiBaseClassAsync {
    private String className;
    private EditText[][] courses;
    private TextView[][] buss;

    public ApiScheduleAsync(Context context, ProgressBar progressBar, EditText[][] courses, String className, TextView[][] buss) {
        super(context, progressBar);
        this.className = className;
        this.courses = courses;
        this.buss = buss;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String passedString) {
        //get les, leerkracht en lokaal
        List<String> olodList = new ArrayList<String>();

        ApiBaseClassAsync api;

        try {
            JSONArray jsonArray = new JSONArray(passedString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject classInfo = jsonArray.getJSONObject(i);
                String tempOlod = classInfo.getString("code_olod");
                olodList.add(tempOlod);
            }
/*
            for (String olod : olodList) {
                api = new ApiGetClassData(super.getContext(), null, courses, buss);
                api.execute("http://data.pxl.be/roosters/v1/klassen/" + className + "/vakken/" + olod);
            }
*/
            for (int i = 0; i < olodList.size(); i++) {
                api = new ApiGetClassData(super.getContext(), null, courses, buss, i==olodList.size()-1);
                api.execute("http://data.pxl.be/roosters/v1/klassen/" + className + "/vakken/" + olodList.get(i));
            }

            super.onPostExecute(passedString);
        } catch (Exception e) {
            //TODO: add alert
        }
    }

    public static boolean isCourseDataAvailable(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("courseApi", context.MODE_PRIVATE);

        return preferences.contains("courseApi");
    }

    //Fills the editTexts with saved courseData
    public static void getCourseData(Context context, EditText[][] courses) {
        SharedPreferences preferences = context.getSharedPreferences("courseApi", context.MODE_PRIVATE);
        String courseString = preferences.getString("courses", null);

        String[] temp = courseString.split(";");
        String[][] courseMatrix = new String[5][10];

        for (int i = 0; i < temp.length; i++) {
            courseMatrix[i] = courseString.split(",");
        }

        for (int i = 0; i < courseMatrix.length; i++) {
            for (int j = 0; j < courseMatrix[i].length; j++) {
                courses[i][j].setText(courseMatrix[i][j]);
                courses[i][j].setEnabled(false);
            }
        }
    }

    //Saves courseData in SharedPreferences
    //A comma seperates columns
    //Semicolon seperates rows
    public static void saveCourseData(EditText[][] courses, Context context) {
        String stringToSave = "";
        StringBuilder builder = new StringBuilder(stringToSave);

        SharedPreferences preferences = context.getSharedPreferences("courseApi", context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        //clear out old data:
        preferencesEditor.clear();//clear out previous data!

        for (int i = 0; i < courses.length; i++) {
            for (int j = 0; j < courses[0].length; j++) {
                builder.append(courses[i][j].getText());
                builder.append(',');
            }
            builder.append(';');
        }

        stringToSave = builder.toString();

        //add class to keyset
        preferencesEditor.putString("courses", stringToSave);
        preferencesEditor.apply();
    }

    public static void deleteCoursedata(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("courseApi", context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        //clear out old data:
        preferencesEditor.clear();
    }
}
