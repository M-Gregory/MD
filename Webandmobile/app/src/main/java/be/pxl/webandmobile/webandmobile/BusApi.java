package be.pxl.webandmobile.webandmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import be.pxl.webandmobile.webandmobile.beans.ApiCallerAsync;

public class BusApi extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_api);

        //1.1 first time data
        Button b = (Button) findViewById(R.id.selectBusStop);
        Intent intent = new Intent(BusApi.this, TempBusShow.class);
        intent.putStringArrayListExtra("passedStringArray", list);

        //2.1 second time data
        //2.2 stuffs for async task...
        ProgressBar pb = (ProgressBar) findViewById(R.id.busApiprogress1);
        TextView tv = (TextView) findViewById(R.id.busApiOverviewStartBus);


        //3.1 during click 1
        b.setOnClickListener(view -> {
            list.clear();//clear out old data (if any)

            ApiCallerAsync api = new ApiCallerAsync(BusApi.this, pb, tv);
            api.execute("https://www.delijn.be/rise-api-search/locations/locatiezoeker/1/neerpelt station");
        });//pass list...

        //4.1 during select
        //list.add( (((EditText)findViewById(R.id.editableText)).getText()).toString() );
        //startActivity(intent);
    }
}
