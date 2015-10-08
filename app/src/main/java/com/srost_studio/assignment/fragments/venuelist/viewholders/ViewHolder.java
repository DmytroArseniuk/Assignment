package com.srost_studio.assignment.fragments.venuelist.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public abstract int getViewType();

}
