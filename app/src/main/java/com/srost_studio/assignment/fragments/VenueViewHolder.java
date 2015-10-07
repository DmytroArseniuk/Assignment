package com.srost_studio.assignment.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.srost_studio.assignment.R;

public class VenueViewHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final TextView distance;

    public VenueViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.venue_name);
        distance = (TextView) itemView.findViewById(R.id.venue_distance);
    }

    public TextView getName() {
        return name;
    }

    public TextView getDistance() {
        return distance;
    }
}
