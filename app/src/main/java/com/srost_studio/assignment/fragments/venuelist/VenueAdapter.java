package com.srost_studio.assignment.fragments.venuelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.srost_studio.assignment.R;
import com.srost_studio.assignment.fragments.venuelist.viewholders.ElemenType;
import com.srost_studio.assignment.fragments.venuelist.viewholders.ProgressBarViewHolder;
import com.srost_studio.assignment.fragments.venuelist.viewholders.VenueViewHolder;
import com.srost_studio.assignment.fragments.venuelist.viewholders.ViewHolder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.condesales.models.Venue;

public class VenueAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Venue> elements;
    private boolean fetching;

    public VenueAdapter(List<Venue> elements) {
        this.elements = elements;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (ElemenType.values()[viewType]) {
            case ELEMENT:
                return new VenueViewHolder(inflater.inflate(R.layout.list_item_venue, parent, false));
            case PROGRESS_BAR:
                return new ProgressBarViewHolder(inflater.inflate(R.layout.list_item_progressbar, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder.getViewType() == ElemenType.ELEMENT.ordinal()){
            ((VenueViewHolder)holder).bind(elements.get(position));
        }
    }


    @Override
    public int getItemCount() {
        if (fetching) {
            return elements.size() + 1;
        }
        return elements.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == elements.size()){
            return ElemenType.PROGRESS_BAR.ordinal();
        }
        return ElemenType.ELEMENT.ordinal();
    }

    public void clear() {
        elements.clear();
    }

    public boolean isEmpty(){
        return elements.isEmpty();
    }

    public void hideProgressBar() {
        fetching = false;
    }

    public void showProgressBar()    {
        fetching = true;
    }

    public boolean isFetching() {
        return fetching;
    }

    public void appendVenues(List<Venue> additionalVenues) {
        if (fetching) {
            hideProgressBar();
        }
        elements.addAll(additionalVenues);
    }

    public void addVenuesWithSort(List<Venue> additionalVenues) {
        appendVenues(additionalVenues);
        Collections.sort(elements, new Comparator<Venue>() {
            @Override
            public int compare(Venue venue, Venue t1) {
                return (int) (venue.getLocation().getDistance() - t1.getLocation().getDistance());
            }
        });
    }
}
