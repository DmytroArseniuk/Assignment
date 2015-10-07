package com.srost_studio.assignment.fragments.venuelist.elements;


import com.srost_studio.assignment.fragments.venuelist.viewholders.VenueViewHolder;
import com.srost_studio.assignment.fragments.venuelist.viewholders.ViewHolder;

import br.com.condesales.models.Venue;

public class VenueElement implements VenueListElement {

    final private Venue venue;

    public VenueElement(Venue venue) {
        this.venue = venue;
    }

    @Override
    public void bind(ViewHolder vh) {
        final VenueViewHolder venueViewHolder = (VenueViewHolder) vh;
        venueViewHolder.getName().setText(venue.getName());
        venueViewHolder.getDistance().setText(String.valueOf(venue.getLocation().getDistance() / 1000.0f));
    }

    @Override
    public int getElementType() {
        return ViewElements.ELEMENT.ordinal();
    }


}
