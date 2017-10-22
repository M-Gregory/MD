package be.pxl.webandmobile.webandmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import be.pxl.webandmobile.webandmobile.beans.ApiBaseClassAsync;
import be.pxl.webandmobile.webandmobile.beans.ApiRoutesAsync;
import be.pxl.webandmobile.webandmobile.beans.ApiSetupClassOne;
import be.pxl.webandmobile.webandmobile.beans.ApiSetupClassTwo;

public class BusCoordinatesToBusRoutes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_coordinates_to_bus_routes);


        //custom
        TextView busApiContent = (TextView) findViewById(R.id.showContentApi);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.busCoordinatesProgressBar);


        //temporary pushing of data:
        ApiSetupClassOne dataObject = (ApiSetupClassOne) getIntent().getSerializableExtra("passedObject");//get data.

        //String resultString = dataObject.getName() + " " + dataObject.getxCoord() + " " + dataObject.getyCoord();
        ApiSetupClassTwo results = getRoute(dataObject, progressBar, busApiContent);//route request to api. (should use the saved data in dataObject (android filesystemm)!
    }

    private ApiSetupClassTwo getRoute(ApiSetupClassOne dataObject, ProgressBar progressBar, TextView textView) {
        ApiBaseClassAsync api = new ApiRoutesAsync(BusCoordinatesToBusRoutes.this, progressBar, textView);
        api.execute("https://www.delijn.be/rise-api-core/reisadvies/routes/" + dataObject.getName() +"/Elfde Liniestraat, Hasselt/" + dataObject.getxCoord() + "/" + dataObject.getyCoord() + "/218829/181291/" + "25-10-2017" + "/08:30" + "/2/on/off/off/off/off/nl");

        return new ApiSetupClassTwo();
    }
}
