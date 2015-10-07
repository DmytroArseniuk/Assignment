package com.srost_studio.assignment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.srost_studio.assignment.PizzaVenueService;
import com.srost_studio.assignment.R;
import com.srost_studio.assignment.events.VenuesFetchedEvent;
import com.srost_studio.assignment.util.EventBus;

import java.util.ArrayList;
import java.util.Collections;

import br.com.condesales.models.Venue;

public class VenueListFragment extends Fragment {

    public static final String FRAGMENT_NAME = VenueListFragment.class.getName();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private VenueAdapter adapter;

    public static VenueListFragment newInstance() {
        VenueListFragment fragment = new VenueListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_venues, container, false);
        adapter = new VenueAdapter(new ArrayList<Venue>());
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);
    }

    private void initView(View contextView) {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) contextView.findViewById(R.id.venue_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Subscribe
    public void accept(VenuesFetchedEvent event) {
        adapter.appendVenues(event.getVenues());
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);
    }
}
