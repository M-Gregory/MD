package be.pxl.webandmobile.webandmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import be.pxl.webandmobile.webandmobile.beans.bus.ApiCoordinatesAsync;
import be.pxl.webandmobile.webandmobile.beans.ApiBaseClassAsync;
import be.pxl.webandmobile.webandmobile.beans.passdata.ApiSetupClassOne;

public class BusApi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_api);

        //1.1 first time data
        //Button button = (Button) findViewById(R.id.selectBusStop);
        EditText editText = (EditText) findViewById(R.id.editableText);

        //2.1 second time data
        //2.2 stuffs for async task...
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.busApiprogress1);
        ListView listView = (ListView) findViewById(R.id.busApiOverviewStartBusListView);

        //3.1 during click 1
        /*
        button.setOnClickListener(view -> {
            if (editText.getText().length() > 0)
                fireEvent(progressBar, listView);
            else
                listView.setAdapter(null);
        });
        */

        //3.2 during enter (keypad):
        editText.setOnEditorActionListener((linkedTextView, i, linkedKeyEvent) -> {
            /*
            if (i == 6 && linkedTextView.getText().length() > 0) {
                fireEvent(progressBar, listView);

                return false;//close menu
            }else if(i==6){
                listView.setAdapter(null);//clear list
            }
            */
            if (linkedTextView.getText().length() > 0 && i == 6) {
                fireEvent(progressBar, listView);
            } else {
                listView.setAdapter(null);//clear list.
            }

            return true;//keep menu open
        });

        //4.1 during select
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            saveData((ApiSetupClassOne) listView.getAdapter().getItem(i));

            finish();//back to main activity!
        });
    }

    private void saveData(ApiSetupClassOne item) {
        //prepare storage variables:
        Context context = getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("busApi", context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        //clear out old data:
        preferencesEditor.clear();//clear out previous data!

        //add xcoord and ycoord to keyset
        preferencesEditor.putString("busName", item.getName());
        preferencesEditor.putInt("busXCoord", item.getxCoord());
        preferencesEditor.putInt("busYCoord", item.getyCoord());
        preferencesEditor.commit();
    }

    private void fireEvent(ProgressBar progressBar, ListView listView) {
        ApiBaseClassAsync api = new ApiCoordinatesAsync(BusApi.this, progressBar, listView);
        api.execute("https://www.delijn.be/rise-api-search/locations/locatiezoeker/5/" + ((EditText) findViewById(R.id.editableText)).getText());//neerpelt omvormen dmv input user...
    }
}
