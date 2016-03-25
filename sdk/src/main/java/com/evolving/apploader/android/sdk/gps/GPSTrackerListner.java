package com.evolving.apploader.android.sdk.gps;

import android.location.Location;

public interface GPSTrackerListner {
    public void onGooglePlayServeiceUnAvailable();
    void onLocationChanged(Location location);

}
