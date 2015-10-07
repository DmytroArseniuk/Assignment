package com.srost_studio.assignment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.otto.Subscribe;
import com.srost_studio.assignment.events.VenuesFetchedEvent;
import com.srost_studio.assignment.fragments.VenueListFragment;
import com.srost_studio.assignment.util.EventBus;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;


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
        final Location centerOfNY = new Location("");
        centerOfNY.setLatitude(40.7406699);
        centerOfNY.setLongitude(-73.9886812);


        googleApiClient.connect();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,
                        VenueListFragment.newInstance(),
                        VenueListFragment.FRAGMENT_NAME)
                .commit();

    }

    @Subscribe
    public void accept(VenuesFetchedEvent event) {
        Log.d("RESTAURANT COUNTER", "" + event.getVenues().size());
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (lastLocation != null) {
            startService(PizzaVenueService.getIntent(MainActivity.this,
                    lastLocation.getLatitude(), lastLocation.getLongitude()));
            Log.d("LAT ", String.valueOf(lastLocation.getLatitude()));
            Log.d("LON ", String.valueOf(lastLocation.getLongitude()));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
}
