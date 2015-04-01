package fr.insa.whatodo.model;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Segolene on 13/03/2015.
 */
public class PlaceFilter  extends Filter {

    protected String place;
    protected boolean isMyLocation;
    protected double latitude;
    protected double longitude;

    public PlaceFilter() {
        super(FilterType.LIEU);
        place="";
        isMyLocation=true;
        longitude = 0;
        latitude = 0;
    }

    public boolean isMyLocation() {
        return isMyLocation;
    }

    public String getTown() {
        return place;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setTown(String town){

        place=town;
        longitude=0;
        latitude=0;
        isMyLocation=false;
    }

    public void setLocation(double longitude, double latitude){
        this.longitude=longitude;
        this.latitude=latitude;
        place="";
        isMyLocation=true;
    }
}