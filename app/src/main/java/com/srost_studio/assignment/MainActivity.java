package com.srost_studio.assignment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.srost_studio.assignment.events.LocationUpdatedEvent;
import com.srost_studio.assignment.fragments.venuelist.VenueListFragment;
import com.srost_studio.assignment.util.EventBus;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private double lastLatitude = 0;
    private double lastLongitude = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);

        googleApiClient.connect();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,
                        VenueListFragment.newInstance(),
                        VenueListFragment.FRAGMENT_NAME)
                .commit();

    }

    @Override
    public void onConnected(Bundle bundle) {
        final Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (lastLocation != null) {
            lastLatitude = lastLocation.getLatitude();
            lastLongitude = lastLocation.getLongitude();
        }
        EventBus.getInstance().post(new LocationUpdatedEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        googleApiClient.disconnect();
        EventBus.getInstance().unregister(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("onConnectionSuspended", "value: " + i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("onConnectionFailed", connectionResult.toString());
    }

    public double getLastLatitude() {
        return lastLatitude;
    }

    public double getLastLongitude() {
        return lastLongitude;
    }
}
