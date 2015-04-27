package fr.insa.whatodo.model;

import android.location.Address;
import android.location.Geocoder;

import java.util.List;
import java.util.Locale;

/**
 * Created by Segolene on 13/03/2015.
 */
public class PlaceFilter  extends Filter {

    protected String place;
    protected boolean sendMyPosition;
    protected double latitude;
    protected double longitude;

    public PlaceFilter() {
        super(FilterType.LIEU);
        place="";
        sendMyPosition=false;
        longitude = 0;
        latitude = 0;
    }

    public boolean isSendMyPosition() {
        return sendMyPosition;
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
        if(town.equalsIgnoreCase("Calcul...")) return; // Annonce de calcul lors de la localisation
        place=town.toUpperCase();
    }

    public void setLocation(double longitude, double latitude){
        this.longitude=longitude;
        this.latitude=latitude;
    }
}