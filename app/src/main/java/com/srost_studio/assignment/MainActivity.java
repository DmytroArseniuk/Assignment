package com.srost_studio.assignment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import br.com.condesales.EasyFoursquare;
import br.com.condesales.EasyFoursquareAsync;
import br.com.condesales.listeners.AccessTokenRequestListener;

public class MainActivity extends AppCompatActivity {

    private EasyFoursquareAsync fq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fq = new EasyFoursquareAsync(this);
        fq.requestAccess(new AccessTokenRequestListener() {
            @Override
            public void onAccessGrant(String accessToken) {
                Log.d("onAccessGrant", accessToken);
            }

            @Override
            public void onError(String errorMsg) {
                Log.d("onError", errorMsg);
            }
        });
    }
}
