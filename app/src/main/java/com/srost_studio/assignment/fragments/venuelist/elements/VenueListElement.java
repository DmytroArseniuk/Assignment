package com.srost_studio.assignment.fragments.venuelist.elements;


import com.srost_studio.assignment.fragments.venuelist.viewholders.ViewHolder;

public interface VenueListElement {
    void bind(ViewHolder vh);
    int getElementType();
}
