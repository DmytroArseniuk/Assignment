package com.srost_studio.assignment.fragments.venuelist;

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
import com.srost_studio.assignment.MainActivity;
import com.srost_studio.assignment.PizzaVenueService;
import com.srost_studio.assignment.R;
import com.srost_studio.assignment.events.LocationUpdatedEvent;
import com.srost_studio.assignment.events.VenuesFetchFailedEvent;
import com.srost_studio.assignment.events.VenuesFetchedEvent;
import com.srost_studio.assignment.util.EventBus;

import java.util.ArrayList;

import br.com.condesales.models.Venue;

public class VenueListFragment extends Fragment {

    public static final String FRAGMENT_NAME = VenueListFragment.class.getName();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private VenueAdapter adapter;
    private boolean venueFetching;
    private MainActivity activity;

    public static VenueListFragment newInstance() {
        VenueListFragment fragment = new VenueListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
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
        recyclerView.setOnScrollListener(onScrollListener);
    }


    @Subscribe
    public void accept(VenuesFetchedEvent event) {
        if(event.getVenues().isEmpty()) {
            adapter.hideProgressBar();
            adapter.notifyDataSetChanged();
            return;
        }
        adapter.appendVenues(event.getVenues());
        adapter.notifyDataSetChanged();
        venueFetching = false;
    }

    @Subscribe
    public void accept(LocationUpdatedEvent event) {
        if(adapter.getItemCount() == 0) {
            final Intent intent = PizzaVenueService.getIntent(activity, activity.getLastLatitude(),
                    activity.getLastLongitude(), adapter.getItemCount());
            activity.startService(intent);
        }
    }

    @Subscribe
    public void accept(VenuesFetchFailedEvent event) {
        //todo Get venues from DB
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);
    }

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            if(adapter.getItemCount()-1 == lastVisibleItemPosition
                    && !venueFetching
                    && adapter.progressbarShown()) {
                final Intent intent = PizzaVenueService.getIntent(activity, activity.getLastLatitude(),
                        activity.getLastLongitude(), adapter.getItemCount());
                activity.startService(intent);
                venueFetching = true;
            }
        }
    };
}
