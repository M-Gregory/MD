package be.pxl.webandmobile.webandmobile.beans;

import android.content.Context;
import android.widget.ProgressBar;
import org.json.JSONArray;

/**
 * Created by René on 23/10/2017.
 */

//TODO: verwerken JSON data
public class ApiScheduleAsync extends ApiBaseScheduleAsync {

    public ApiScheduleAsync(Context context, ProgressBar progressBar) {
        super(context, progressBar);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);

    }
}
