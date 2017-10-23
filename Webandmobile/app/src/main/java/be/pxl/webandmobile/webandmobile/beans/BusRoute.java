package be.pxl.webandmobile.webandmobile.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 11400136 on 22/10/2017.
 */

public class BusRoute {
    //properties(final):
    private final String pxlStopName = "Halte Hasselt Zwembad (403430)";
    private final String xCoordPxl = "218786";
    private final String yCoordPxl = "181067";
    private final int amountOfResults = 5;
    private final String baseRouteUrl = "https://www.delijn.be/rise-api-core/reisadvies/routes/{startPoint}/{endPoint}/{startX}/{startY}/{endX}/{endY}/{date}/{time}/{arrivalDeparture}/{byBus}/{byTram}/{byMetro}/{byTrain}/off/nl";

    private String[] baseRouteUrlParameters = new String[13];
    private boolean isToSchool;

    //constructor
    public BusRoute() {
        //set buus only as default:
        baseRouteUrlParameters[9] = "on";//bus
        baseRouteUrlParameters[10] = "off";//tram
        baseRouteUrlParameters[11] = "off";//metro
        baseRouteUrlParameters[12] = "off";//train
    }

    public void setStartX(int xCoord) {
        this.baseRouteUrlParameters[2] = String.valueOf(xCoord);
    }

    public void setStartY(int yCoord) {
        this.baseRouteUrlParameters[3] = String.valueOf(yCoord);
    }

    public void setDestX(int xCoord) {
        this.baseRouteUrlParameters[4] = String.valueOf(xCoord);
    }

    public void setDestY(int yCoord) {
        this.baseRouteUrlParameters[5] = String.valueOf(yCoord);
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

    public void setToSchool(boolean isToSchool) {
        this.isToSchool = isToSchool;

        if (isToSchool)
            baseRouteUrlParameters[0] = "userStop";
        else
            baseRouteUrlParameters[1] = "userStop";
    }

    public String generateUrl() throws IllegalArgumentException {
        String result;

        //Rebuild based on direction of route:
        if (isToSchool) {
            baseRouteUrlParameters[1] = pxlStopName;

            if (!baseRouteUrlParameters[4].equals(xCoordPxl)) {
                baseRouteUrlParameters[2] = baseRouteUrlParameters[4];
                baseRouteUrlParameters[4] = xCoordPxl;
            }

            if (!baseRouteUrlParameters[5].equals(yCoordPxl)) {
                baseRouteUrlParameters[3] = baseRouteUrlParameters[5];
                baseRouteUrlParameters[5] = yCoordPxl;
            }
        } else {
            baseRouteUrlParameters[0] = pxlStopName;

            if (!baseRouteUrlParameters[2].equals(xCoordPxl)) {
                baseRouteUrlParameters[4] = baseRouteUrlParameters[2];
                baseRouteUrlParameters[2] = xCoordPxl;
            }

            if (!baseRouteUrlParameters[3].equals(yCoordPxl)) {
                baseRouteUrlParameters[5] = baseRouteUrlParameters[3];
                baseRouteUrlParameters[3] = yCoordPxl;
            }
        }

        //test all fields (MUST BE AFTER ABOVE final filler!):
        for (String parameter : baseRouteUrlParameters) {
            if (parameter == null)
                throw new IllegalArgumentException("All fields must be filled in!");
        }

        //Build url
        result = baseRouteUrl + "";//concat for new string, can't edit the final one -_-
        result = result.replace("{startPoint}", baseRouteUrlParameters[0]);
        result = result.replace("{endPoint}", baseRouteUrlParameters[1]);
        result = result.replace("{startX}", baseRouteUrlParameters[2]);
        result = result.replace("{startY}", baseRouteUrlParameters[3]);
        result = result.replace("{endX}", baseRouteUrlParameters[4]);
        result = result.replace("{endY}", baseRouteUrlParameters[5]);
        result = result.replace("{date}", baseRouteUrlParameters[6]);
        result = result.replace("{time}", baseRouteUrlParameters[7]);
        result = result.replace("{arrivalDeparture}", baseRouteUrlParameters[8]);
        result = result.replace("{byBus}", baseRouteUrlParameters[9]);
        result = result.replace("{byTram}", baseRouteUrlParameters[10]);
        result = result.replace("{byMetro}", baseRouteUrlParameters[11]);
        result = result.replace("{byTrain}", baseRouteUrlParameters[12]);


        return result;
    }
}
