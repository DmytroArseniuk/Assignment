package com.srost_studio.assignment.fragments.venuelist.viewholders;


import android.view.View;

public class ProgressBarViewHolder extends ViewHolder {

    public ProgressBarViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getViewType() {
        return ElemenType.PROGRESS_BAR.ordinal();
    }
}
