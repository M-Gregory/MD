package be.pxl.webandmobile.webandmobile.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 11400136 on 22/10/2017.
 */

public class BusRoute {
    //properties(final):
    private final int amountOfResults = 5;
    private final String baseCoordinatesUrl = "https://www.delijn.be/rise-api-search/locations/locatiezoeker/{amount_of_results}/{place}";
    private final String baseRouteUrl = "https://www.delijn.be/rise-api-core/reisadvies/routes/{startPoint}/{endPoint}/{startX}/{startY}/{endX}/{endY}/{date}/{time}/{arrivalDeparture}/{byBus}/{byTram}/{byMetro}/{byTrain}/off/nl";

    private String[] baseRouteUrlParameters = new String[13];

    //constructor
    public BusRoute() {

    }

    //methods:
    public void setStartLocation(String location){
        String getStopDetails = baseCoordinatesUrl.replace("{amount_of_results}", Integer.toString(amountOfResults));
        getStopDetails.replace("{place}", location);


        //TODO: fill in the blanks of baseRouteUrl...

        System.out.println(getStopDetails);
    }

    public void setDateAndTime(Date date){
        String[] data = (new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date)).split(" ");

        this.baseRouteUrlParameters[6]=data[0];
        this.baseRouteUrlParameters[7]=data[1];
    }

    public void isArrivaltime(boolean arrival){
        if(arrival){
            this.baseRouteUrlParameters[8] = "2";
        }else{
            this.baseRouteUrlParameters[8] = "1";
        }
    }


}
