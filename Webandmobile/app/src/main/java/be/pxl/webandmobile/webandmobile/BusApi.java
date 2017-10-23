package be.pxl.webandmobile.webandmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.HashSet;
import java.util.Set;

import be.pxl.webandmobile.webandmobile.beans.ApiCoordinatesAsync;
import be.pxl.webandmobile.webandmobile.beans.ApiBaseClassAsync;
import be.pxl.webandmobile.webandmobile.beans.ApiSetupClassOne;

public class BusApi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_api);

        //1.1 first time data
        Button button = (Button) findViewById(R.id.selectBusStop);

        //2.1 second time data
        //2.2 stuffs for async task...
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.busApiprogress1);
        ListView listView = (ListView) findViewById(R.id.busApiOverviewStartBusListView);

        //3.1 during click 1
        button.setOnClickListener(view -> {
            ApiBaseClassAsync api = new ApiCoordinatesAsync(BusApi.this, progressBar, listView);
            api.execute("https://www.delijn.be/rise-api-search/locations/locatiezoeker/5/neerpelt station");//neerpelt omvormen dmv input user...
        });

        //4.1 during select
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(BusApi.this, BusCoordinatesToBusRoutes.class);
            intent.putExtra("passedObject", (ApiSetupClassOne) listView.getAdapter().getItem(i));

            saveData((ApiSetupClassOne) listView.getAdapter().getItem(i));

            startActivity(intent);
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
        preferencesEditor.putInt( "busXCoord",item.getxCoord() );
        preferencesEditor.putInt( "busYCoord",item.getyCoord() );
        preferencesEditor.commit();

        //int x = preferences.getInt("busXCoord", 0);//0=fallback value
        //int y = preferences.getInt("busYCoord", 0);//0=fallback value






        //====================================================================================
        //informative purposes only:
        //--------------------------
        //you can use a set like this:
        //add:
        //----
        //Set<String> mySet = new HashSet<>();
        //mySet.add("string value 1");
        //mySet.add("string value 2");
        //preferencesEditor.putStringSet("busApiCoordinates", mySet);//add to local storage:

        //get:
        //----
        //Object o = preferences.getAll();
        //o = preferences.getInt("busXCoord", 0);
        //o = preferences.getStringSet("busApiCoordinates", new HashSet<>());
        //====================================================================================
    }
}
