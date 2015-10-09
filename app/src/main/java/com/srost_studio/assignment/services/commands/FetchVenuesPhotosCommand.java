package com.srost_studio.assignment.services.commands;

import android.content.Intent;
import android.util.Log;

import com.srost_studio.assignment.MainApplication;
import com.srost_studio.assignment.events.PhotosFetchEvent;
import com.srost_studio.assignment.events.PhotosFetchFailedEvent;
import com.srost_studio.assignment.services.backend.PhotoResponsePOJO;
import com.srost_studio.assignment.util.EventBus;

import retrofit.RetrofitError;

import static br.com.condesales.constants.FoursquareConstants.API_DATE_VERSION;
import static br.com.condesales.constants.FoursquareConstants.CLIENT_ID;
import static br.com.condesales.constants.FoursquareConstants.CLIENT_SECRET;

public class FetchVenuesPhotosCommand implements Command {

    public static final String FETCH_VENUES_PHOTOS_COMMAND = "fetch_venues_photos_command";
    public static final String VENUE_ID = "venue_id";


    @Override
    public void execute(MainApplication application, Intent intent) {

        final String venueId = intent.getStringExtra(VENUE_ID);

        PhotoResponsePOJO response = null;

        try {
            response = application.getFoursquareBackendService().getVenuePhotos(venueId,
                    API_DATE_VERSION, CLIENT_ID, CLIENT_SECRET);
        } catch (RetrofitError error) {
            Log.d("ERROR", error.toString());
            EventBus.getInstance().post(new PhotosFetchFailedEvent());
            return;
        }

        EventBus.getInstance().post(new PhotosFetchEvent(response.response.photos));
    }
}
