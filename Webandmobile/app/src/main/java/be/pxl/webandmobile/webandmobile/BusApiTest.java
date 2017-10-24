package be.pxl.webandmobile.webandmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import be.pxl.webandmobile.webandmobile.beans.ApiBaseClassAsync;
import be.pxl.webandmobile.webandmobile.beans.ApiRoutesAsync;
import be.pxl.webandmobile.webandmobile.beans.BusRoute;

public class BusApiTest extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_api_test);

        TextView tv = (TextView) findViewById(R.id.showBusApiTestData);
        ProgressBar pb = (ProgressBar)findViewById(R.id.busApiTestProgressBar);

        try {
            String url = loadData();

            sendUrl(url, pb, tv);

        } catch (IOException e) {
            e.printStackTrace();
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    private String loadData() throws IOException, IllegalArgumentException {
        //Prepare stored variables:
        Context context = getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("busApi", context.MODE_PRIVATE);

        String stopName = preferences.getString("busName", "startPoint");
        int xCoordinate = preferences.getInt("busXCoord", 0);
        int yCoordinate = preferences.getInt("busYCoord", 0);

        //test stored variables:
        if (xCoordinate == 0 || yCoordinate == 0)
            throw new IOException("Unable to read bus data, you should set these first");

        //start building the url:
        Date d = (new GregorianCalendar(2017, 11, 10, 12, 45)).getTime();//OM TE TESTEN, MOET AAN DE LESROOSTER GEKOPPELD WORDEN! -> 10-11-2017 12:45u

        //opstellen busroute:
        BusRoute myBusRoute = new BusRoute();
        myBusRoute.setBusStop(stopName);
        myBusRoute.setDateAndTime(d);
        myBusRoute.setDestX(xCoordinate);
        myBusRoute.setDestY(yCoordinate);

        myBusRoute.setToSchool(true);//to school, false when going home!!!
        myBusRoute.isArrivaltime(true);//you wish to be at school at: 20-11-2017 12:45u -> false would be you start you're trip at 20-11-2017 12:45u

        //return url:
        String t1 = myBusRoute.generateUrl();
        String t2 = myBusRoute.generateUrl();

        boolean test = t1.equals(t2);

        return myBusRoute.generateUrl();//throws 'IllegalArgumentException'!
    }

    private void sendUrl(String url, ProgressBar progressBar, TextView textview) {
        ApiBaseClassAsync api = new ApiRoutesAsync(BusApiTest.this, progressBar, textview);
        api.execute(url);
    }
}
