package com.srost_studio.assignment.services;

import android.location.Location;
import android.os.AsyncTask;

import com.srost_studio.assignment.db.repository.VenueRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.condesales.models.Venue;

public class VenueService {

    private final VenueRepository repository;

    public VenueService(VenueRepository repository) {
        this.repository = repository;
    }

    public ArrayList<Venue> findVenuesNearLocation(double latitude, double longitude, double radius) {
        final ArrayList<Venue> allVenues = repository.findAll();
        final ArrayList<Venue> result = new ArrayList<>();
        final Location currentLocation = new Location("Current");
        currentLocation.setLatitude(latitude);
        currentLocation.setLongitude(longitude);

        for (Venue venue : allVenues) {
            if (getDistance(currentLocation, venue.getLocation()) <= radius) {
                result.add(venue);
            }
        }

        Collections.sort(result, new Comparator<Venue>() {
            @Override
            public int compare(Venue venue, Venue t1) {
                return (int)(getDistance(currentLocation, venue.getLocation())
                        - getDistance(currentLocation, t1.getLocation()));
            }
        });

        return result;
    }

    public void saveAll(ArrayList<Venue> venues) {
        repository.saveAll(venues);
    }

    private double getDistance(Location currentLocation, br.com.condesales.models.Location venueLocation) {
        final Location other = new Location("other");
        other.setLatitude(venueLocation.getLat());
        other.setLongitude(venueLocation.getLng());
        return currentLocation.distanceTo(other);
    }

    public void findVenuesNearLocationAsync(final double latitude, final double longitude, final double radius, final VenueRepositoryCallback callback) {
        new AsyncTask<Void, Void, ArrayList<Venue>>(){

            @Override
            protected ArrayList<Venue> doInBackground(Void... voids) {
                return findVenuesNearLocation(latitude, longitude, radius);
            }

            @Override
            protected void onPostExecute(ArrayList<Venue> venues) {
                super.onPostExecute(venues);
                callback.onPostExecute(venues);
            }
        }.execute();
    }

    public interface VenueRepositoryCallback {
        void onPostExecute(ArrayList<Venue> venues);
    }


}
