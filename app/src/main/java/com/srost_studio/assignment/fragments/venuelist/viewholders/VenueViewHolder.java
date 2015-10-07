package com.srost_studio.assignment.fragments.venuelist.viewholders;

import android.view.View;
import android.widget.TextView;

import com.srost_studio.assignment.R;
import com.srost_studio.assignment.fragments.venuelist.elements.ViewElements;

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
        return ViewElements.ELEMENT.ordinal();
    }

    public TextView getName() {
        return name;
    }

    public TextView getDistance() {
        return distance;
    }


}
