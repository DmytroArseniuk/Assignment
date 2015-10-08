package com.srost_studio.assignment;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.srost_studio.assignment.db.DatabaseManager;
import com.srost_studio.assignment.db.repository.VenueRepository;
import com.srost_studio.assignment.services.VenueService;
import com.srost_studio.assignment.services.backend.FoursquareBackendService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class MainApplication extends Application {


    private DatabaseManager databaseManager;
    private VenueService venueService;
    private FoursquareBackendService foursquareBackendService;
    private final String baseURL = "https://api.foursquare.com/v2";

    @Override
    public void onCreate() {
        super.onCreate();

        databaseManager = new DatabaseManager(this);

        final VenueRepository venueRepository = new VenueRepository(databaseManager);
        venueService = new VenueService(venueRepository);


        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseURL)
                .setConverter(new GsonConverter(gson))
                .build();


        foursquareBackendService = restAdapter.create(FoursquareBackendService.class);
    }

    public VenueService getVenueService() {
        return venueService;
    }

    public FoursquareBackendService getFoursquareBackendService() {
        return foursquareBackendService;
    }

    @Override
    public void onTerminate() {
        databaseManager.getDatabase().close();
        super.onTerminate();

    }
}
