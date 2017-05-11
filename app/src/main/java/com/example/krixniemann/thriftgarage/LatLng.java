package com.example.krixniemann.thriftgarage;


/**
 * Created by krixniemann on 5/10/2017.
 */

public final class LatLng
         {
    public final double latitude;
    public final double longitude;

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
public double getLatitude(){
    return latitude;
}
    public double getLongitude(){
        return longitude;
    }

}