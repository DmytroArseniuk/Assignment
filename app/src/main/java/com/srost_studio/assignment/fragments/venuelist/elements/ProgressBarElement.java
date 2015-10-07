package com.srost_studio.assignment.fragments.venuelist.elements;

import com.srost_studio.assignment.fragments.venuelist.viewholders.ViewHolder;

public class ProgressBarElement implements VenueListElement {

    @Override
    public void bind(ViewHolder vh) {

    }

    @Override
    public int getElementType() {
        return ViewElements.PROGRESS_BAR.ordinal();
    }
}
