package es.craftsmanship.toledo.katangapp.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author Cristóbal Hermida
 */
public class GPSTracker extends Service implements LocationListener {


    private final Context mContext;


    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    private Location location; // location

    private double latitude; // latitude

    private double longitude; // longitude


    public GPSTracker(Context context) {
        this.mContext = context;
    }


    public double getLatitude(){

        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }


    public double getLongitude(){

        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}