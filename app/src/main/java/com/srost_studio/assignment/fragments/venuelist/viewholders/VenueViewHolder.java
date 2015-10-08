package com.srost_studio.assignment.fragments.venuelist.viewholders;

import android.view.View;
import android.widget.TextView;

import com.srost_studio.assignment.R;

import br.com.condesales.models.Venue;

public class VenueViewHolder extends ViewHolder {

    private final TextView name;
    private final TextView distance;

    public VenueViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.venue_name);
        distance = (TextView) itemView.findViewById(R.id.venue_distance);
    }

    @Override
    public int getViewType() {
        return ElemenType.ELEMENT.ordinal();
    }

    public void bind(Venue venue) {
        name.setText(venue.getName());
        distance.setText(String.valueOf(venue.getLocation().getDistance() / 1000.0f));
    }

    public TextView getName() {
        return name;
    }

    public TextView getDistance() {
        return distance;
    }


}
