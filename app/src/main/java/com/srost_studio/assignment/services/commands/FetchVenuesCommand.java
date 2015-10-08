package com.srost_studio.assignment.services.commands;

import android.content.Intent;
import android.util.Log;

import com.srost_studio.assignment.MainApplication;
import com.srost_studio.assignment.events.VenuesFetchFailedEvent;
import com.srost_studio.assignment.events.VenuesFetchedEvent;
import com.srost_studio.assignment.services.backend.ExploreResponsePOJO;
import com.srost_studio.assignment.util.EventBus;

import java.util.ArrayList;

import br.com.condesales.models.Group;
import br.com.condesales.models.Venue;
import retrofit.RetrofitError;

import static br.com.condesales.constants.FoursquareConstants.API_DATE_VERSION;
import static br.com.condesales.constants.FoursquareConstants.CLIENT_ID;
import static br.com.condesales.constants.FoursquareConstants.CLIENT_SECRET;

public class FetchVenuesCommand implements Command {

    public static final String FETCH_VENUE_COMMAND = "fetch_venue_command";
    public static final String LATITUDE_TAG = "latitude_tag";
    public static final String LONGITUDE_TAG = "longitude_tag";
    public static final String OFFSET_TAG = "offset_tag";

    private final String query = "pizza";
    private final int queryLimit = 10;


    @Override
    public void execute(MainApplication application, Intent intent) {
        final double latitude = intent.getDoubleExtra(LATITUDE_TAG, 0);
        final double longitude = intent.getDoubleExtra(LONGITUDE_TAG, 0);
        final int offset = intent.getIntExtra(OFFSET_TAG, 0);

        ExploreResponsePOJO response = null;
        try {
            response = application.getFoursquareBackendService().findVenues(API_DATE_VERSION, latitude + "," + longitude,
                    query, queryLimit, offset, CLIENT_ID, CLIENT_SECRET);
        } catch (RetrofitError error) {
            Log.d("ERROR", error.toString());
            EventBus.getInstance().post(new VenuesFetchFailedEvent());
            return;
        }
        ArrayList<Venue> venues = new ArrayList<>();


        for (Group group : response.response.groups) {
            for (Group.GroupItem item : group.items) {
                venues.add(item.venue);
            }
        }

        application.getVenueService().saveAll(venues);

        EventBus.getInstance().post(new VenuesFetchedEvent(venues));
    }
}
