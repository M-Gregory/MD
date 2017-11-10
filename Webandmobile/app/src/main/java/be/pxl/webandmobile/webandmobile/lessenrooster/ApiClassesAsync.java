package be.pxl.webandmobile.webandmobile.lessenrooster;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.pxl.webandmobile.webandmobile.R;
import be.pxl.webandmobile.webandmobile.beans.ApiBaseClassAsync;

/**
 * Created by René on 23/10/2017.
 */

//Gets the classes from the selected year/specialization
public class ApiClassesAsync extends ApiBaseClassAsync {
    private Spinner spinner;

    public ApiClassesAsync(Context context, Spinner spinner, ProgressBar progressBar) {
        super(context, progressBar);

        this.spinner = spinner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String passedClasses) {
        //verwerk klasses in de spinner
        List<String> classList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(passedClasses);

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject classValue = jsonArray.getJSONObject(i);
                    String tempClass = classValue.getString("klas");
                    classList.add(tempClass);
                }
        } catch (JSONException e) {
            //classList = null;
            e.printStackTrace();
        }

        super.onPostExecute(passedClasses);
        spinner.setAdapter(new ArrayAdapter<String>(super.getContext(), R.layout.support_simple_spinner_dropdown_item, classList));
    }
}
