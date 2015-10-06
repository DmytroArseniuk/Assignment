package com.srost_studio.assignment;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import br.com.condesales.models.Venue;

import static br.com.condesales.constants.FoursquareConstants.API_DATE_VERSION;
import static br.com.condesales.constants.FoursquareConstants.CLIENT_ID;
import static br.com.condesales.constants.FoursquareConstants.CLIENT_SECRET;

public class PizzaVenueService extends IntentService {
    public static final String LATITUDE_TAG = "latitude_tag";
    public static final String LONGITUDE_TAG = "longitude_tag";
    public static final String OFFSET_TAG = "offset_tag";
    private final String query = "pizza";
    private final int queryLimit = 10;


    public PizzaVenueService() {
        super("VenueService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final double latitude = intent.getDoubleExtra(LATITUDE_TAG, 0);
        final double longitude = intent.getDoubleExtra(LONGITUDE_TAG, 0);
        final int offset = intent.getIntExtra(OFFSET_TAG, 0);

        ArrayList<Venue> venues = new ArrayList<Venue>();

        try {
            final String apiDateVersion = API_DATE_VERSION;
            StringBuilder uriBuilder = new StringBuilder();
            uriBuilder.append("https://api.foursquare.com/v2/venues/search")
                    .append("?v=").append(apiDateVersion)
                    .append("&ll=").append(latitude).append(",").append(longitude)
                    .append("&query=").append(query)
                    .append("&limit=").append(queryLimit)
                    .append("&offset=").append(offset)
                    .append("&client_id=").append(CLIENT_ID)
                    .append("&client_secret=").append(CLIENT_SECRET);

            JSONObject venuesJson = executeHttpGet(uriBuilder.toString());

            int returnCode = Integer.parseInt(venuesJson.getJSONObject("meta").getString("code"));
            if (returnCode == 200) {
                Gson gson = new Gson();
                JSONArray json = venuesJson.getJSONObject("response")
                        .getJSONArray("venues");
                for (int i = 0; i < json.length(); i++) {
                    Venue venue = gson.fromJson(json.getJSONObject(i)
                            .toString(), Venue.class);
                    venues.add(venue);
                }
            } else {
                Log.d("ERROR", venuesJson.getJSONObject("meta").getString("errorDetail"));
            }

        } catch (Exception exp) {
            Log.d("ERROR", exp.toString());
        }

        Log.d("RESULT", "OKAY");
    }


    public JSONObject executeHttpGet(String uri) throws IOException, JSONException {
        final URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        final InputStream in = new BufferedInputStream(conn.getInputStream());
        return convertInputStreamToJson(in);
    }

    private JSONObject convertInputStreamToJson(InputStream inputStream) throws IOException, JSONException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = bufferedReader.readLine()) != null) {
            sb.append(s);
        }
        bufferedReader.close();
        inputStream.close();
        return new JSONObject(sb.toString());

    }
}