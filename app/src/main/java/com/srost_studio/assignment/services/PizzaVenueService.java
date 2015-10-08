package com.srost_studio.assignment.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.srost_studio.assignment.MainApplication;
import com.srost_studio.assignment.services.commands.Command;
import com.srost_studio.assignment.services.commands.FetchVenuesCommand;

import java.util.HashMap;

public class PizzaVenueService extends IntentService {

    public static final String COMMAND = "command";
    public static final HashMap<String, Command> commands = new HashMap<String, Command>() {{
        put(FetchVenuesCommand.FETCH_VENUE_COMMAND, new FetchVenuesCommand());
    }};

    public static void fetchPizzaVenues(Context context, double latitude, double longitude, int offset) {
        final Intent intent = new Intent(context, PizzaVenueService.class);
        intent.putExtra(PizzaVenueService.COMMAND, FetchVenuesCommand.FETCH_VENUE_COMMAND);
        intent.putExtra(FetchVenuesCommand.LATITUDE_TAG, latitude);
        intent.putExtra(FetchVenuesCommand.LONGITUDE_TAG, longitude);
        intent.putExtra(FetchVenuesCommand.OFFSET_TAG, offset);
        context.startService(intent);
    }

    public PizzaVenueService() {
        super("VenueService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String commandName = intent.getStringExtra(COMMAND);
        if (commandName != null) {
            commands.get(commandName).execute((MainApplication)getApplication(), intent);
        }
    }

}
