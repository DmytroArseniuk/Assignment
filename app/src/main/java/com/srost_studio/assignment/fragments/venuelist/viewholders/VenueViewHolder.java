package com.srost_studio.assignment.fragments.venuelist.viewholders;

import android.view.View;
import android.widget.TextView;

import com.srost_studio.assignment.R;
import com.srost_studio.assignment.util.DistanceUtil;

import br.com.condesales.models.Venue;

public class VenueViewHolder extends ViewHolder {

    private final TextView name;
    private final TextView distance;
    private String venueId;

    public VenueViewHolder(View itemView, final VenueItemClickListener listener) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.venue_name);
        distance = (TextView) itemView.findViewById(R.id.venue_distance);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(venueId);
            }
        });
    }

    @Override
    public int getViewType() {
        return ElemenType.ELEMENT.ordinal();
    }

    public void bind(Venue venue) {
        venueId = venue.getId();
        name.setText(venue.getName());
        distance.setText(DistanceUtil.getDisplayableDistance(venue.getLocation().getDistance()));
    }

}
