package com.srost_studio.assignment.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.srost_studio.assignment.R;

public class VenueList extends Fragment {

    public static final String FRAGMENT_NAME = VenueList.class.getName();

    public static VenueList newInstance() {
        VenueList fragment = new VenueList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_venues, container, false);

        return v;
    }

}
