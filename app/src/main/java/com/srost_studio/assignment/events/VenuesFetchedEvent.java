package com.srost_studio.assignment.events;

import java.util.ArrayList;

import br.com.condesales.models.Venue;

public class VenuesFetchedEvent {
    final private ArrayList<Venue> venues;

    public VenuesFetchedEvent(ArrayList<Venue> venues) {
        this.venues = venues;
    }

    public ArrayList<Venue> getVenues() {
        return venues;
    }
}
