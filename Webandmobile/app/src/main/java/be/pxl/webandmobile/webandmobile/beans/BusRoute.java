package be.pxl.webandmobile.webandmobile.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 11400136 on 22/10/2017.
 */

public class BusRoute {
    //properties(final):
    private final int amountOfResults = 5;
    private final String baseRouteUrl = "https://www.delijn.be/rise-api-core/reisadvies/routes/{startPoint}/{endPoint}/{startX}/{startY}/{endX}/{endY}/{date}/{time}/{arrivalDeparture}/{byBus}/{byTram}/{byMetro}/{byTrain}/off/nl";

    private String[] baseRouteUrlParameters = new String[13];

    //constructor
    public BusRoute() {
        //Only for visuals so putting something random in it:
        baseRouteUrlParameters[0] = "StartPoint";
        baseRouteUrlParameters[1] = "EndPoint";

        //set buus only as default:
        baseRouteUrlParameters[9] = "on";//bus
        baseRouteUrlParameters[10] = "off";//tram
        baseRouteUrlParameters[11] = "off";//metro
        baseRouteUrlParameters[12] = "off";//train
    }

    public void setStartX(String xCoord) {
        this.baseRouteUrlParameters[2] = xCoord;
    }

    public void setStartY(String yCoord) {
        this.baseRouteUrlParameters[3] = yCoord;
    }

    public void setDestX(String xCoord) {
        this.baseRouteUrlParameters[4] = xCoord;
    }

    public void setDestY(String yCoord) {
        this.baseRouteUrlParameters[5] = yCoord;
    }

    public void setDateAndTime(Date date) {
        String[] data = (new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date)).split(" ");

        this.baseRouteUrlParameters[6] = data[0];
        this.baseRouteUrlParameters[7] = data[1];
    }

    public void isArrivaltime(boolean arrival) {
        if (arrival) {
            this.baseRouteUrlParameters[8] = "2";
        } else {
            this.baseRouteUrlParameters[8] = "1";
        }
    }
}
