package com.srost_studio.assignment.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.srost_studio.assignment.R;

import java.util.ArrayList;
import java.util.List;

import br.com.condesales.models.Venue;

public class VenueAdapter extends RecyclerView.Adapter<VenueViewHolder>{

    private List<Venue> venues;

    public VenueAdapter(List<Venue> venues) {
        this.venues = venues;
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new VenueViewHolder(inflater.inflate(R.layout.list_item_venue, parent, false));
    }

    @Override
    public void onBindViewHolder(VenueViewHolder holder, int position) {
        final Venue venue = venues.get(position);
        holder.getName().setText(venue.getName());
        holder.getDistance().setText(String.valueOf(venue.getLocation().getDistance()/1000.0f));
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public void appendVenues(List<Venue> additionalVenues) {
        venues.addAll(additionalVenues);
    }
}
