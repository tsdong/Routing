package com.codeit.priorityrouting;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Ted on 3/25/2015.
 */
public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        //I make a log to see the results

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}