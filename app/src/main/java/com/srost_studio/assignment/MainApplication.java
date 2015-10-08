package com.srost_studio.assignment;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.srost_studio.assignment.db.DatabaseManager;
import com.srost_studio.assignment.db.repository.VenueRepository;
import com.srost_studio.assignment.services.backend.FoursquareBackendService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class MainApplication extends Application {


    private DatabaseManager databaseManager;
    private VenueRepository venueRepository;
    private FoursquareBackendService foursquareBackendService;
    private final String baseURL = "https://api.foursquare.com/v2";

    @Override
    public void onCreate() {
        super.onCreate();

        databaseManager = new DatabaseManager(this);
        venueRepository = new VenueRepository(databaseManager);


        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseURL)
                .setConverter(new GsonConverter(gson))
                .build();


        foursquareBackendService = restAdapter.create(FoursquareBackendService.class);
    }

    public VenueRepository getVenueRepository() {
        return venueRepository;
    }

    public FoursquareBackendService getFoursquareBackendService() {
        return foursquareBackendService;
    }
}
