package be.pxl.webandmobile.webandmobile.beans;

import java.io.Serializable;

/**
 * Created by 11400136 on 22/10/2017.
 */

public class ApiSetupClassOne implements Serializable {
    private int xCoord;
    private int yCoord;
    private String name;

    public ApiSetupClassOne() {

    }

    public ApiSetupClassOne(String name, int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.name = name;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
