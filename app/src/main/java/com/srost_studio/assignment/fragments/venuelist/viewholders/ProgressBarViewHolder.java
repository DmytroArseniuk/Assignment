package com.srost_studio.assignment.fragments.venuelist.viewholders;


import android.view.View;

import com.srost_studio.assignment.fragments.venuelist.elements.ViewElements;

public class ProgressBarViewHolder extends ViewHolder {

    public ProgressBarViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getViewType() {
        return ViewElements.PROGRESS_BAR.ordinal();
    }
}
