package com.srost_studio.assignment.fragments.venuelist;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.srost_studio.assignment.MainActivity;
import com.srost_studio.assignment.MainApplication;
import com.srost_studio.assignment.R;
import com.srost_studio.assignment.events.LocationUpdatedEvent;
import com.srost_studio.assignment.events.VenuesFetchFailedEvent;
import com.srost_studio.assignment.events.VenuesFetchedEvent;
import com.srost_studio.assignment.services.PizzaVenueService;
import com.srost_studio.assignment.services.VenueService;
import com.srost_studio.assignment.util.EventBus;

import java.util.ArrayList;

import br.com.condesales.models.Venue;

import static com.srost_studio.assignment.fragments.venuelist.VenueListFragment.SelectedLocation.CENTER_OF_NY;
import static com.srost_studio.assignment.fragments.venuelist.VenueListFragment.SelectedLocation.MY_LOCATION;

public class VenueListFragment extends Fragment {

    public static final String FRAGMENT_NAME = VenueListFragment.class.getName();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private VenueAdapter adapter;
    private boolean venueFetching;
    private boolean possibleToFetchMore;
    private final double nearbyVenueRadius = 40_000.0;
    private Location centerOfNY;
    private SelectedLocation selectedLocation = MY_LOCATION;


    public static VenueListFragment newInstance() {
        VenueListFragment fragment = new VenueListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        possibleToFetchMore = true;
        centerOfNY = new Location("");
        centerOfNY.setLatitude(40.7406699);
        centerOfNY.setLongitude(-73.9886812);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_venues, container, false);
        adapter = new VenueAdapter(new ArrayList<Venue>());
        adapter.showProgressBar();
        adapter.notifyDataSetChanged();
        setHasOptionsMenu(true);
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
        venueFetching = false;
        if (event.getVenues().isEmpty()) {
            adapter.hideProgressBar();
            adapter.notifyDataSetChanged();
            possibleToFetchMore = false;
            return;
        }
        adapter.addVenuesWithSort(event.getVenues());
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void accept(LocationUpdatedEvent event) {
        if (adapter.isEmpty()) {
            fetchVenues();
        }
    }

    @Subscribe
    public void accept(VenuesFetchFailedEvent event) {
        venueFetching = false;
        adapter.hideProgressBar();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_my_location:
                if (!venueFetching && selectedLocation != MY_LOCATION) {
                    changeLocation(MY_LOCATION);
                }
                return true;

            case R.id.action_ny_location:
                if (!venueFetching && selectedLocation != CENTER_OF_NY) {
                    changeLocation(CENTER_OF_NY);
                }
                return true;
        }

        return false;
    }

    private void changeLocation(SelectedLocation selectedLocation) {
        this.selectedLocation = selectedLocation;
        possibleToFetchMore = true;
        adapter.clear();
        adapter.showProgressBar();
        adapter.notifyDataSetChanged();
        fetchVenues();
    }

    private void fetchVenues() {
        if (!isNetworkAvailable()) {
            ((MainApplication) getActivity().getApplication())
                    .getVenueService().findVenuesNearLocationAsync(getLastLatitude(), getLastLongitude(), nearbyVenueRadius, new VenueService.VenueRepositoryCallback() {
                @Override
                public void onPostExecute(ArrayList<Venue> venues) {
                    adapter.appendVenues(venues);
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            final int adapterSize = adapter.isFetching() ? adapter.getItemCount() : adapter.getItemCount() - 1;
            PizzaVenueService.fetchPizzaVenues(getActivity(), getLastLatitude(), getLastLongitude(), adapterSize);
        }
    }

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            if (isNetworkAvailable() && adapter.getItemCount() - 1 == lastVisibleItemPosition
                    && !venueFetching && possibleToFetchMore) {
                PizzaVenueService.fetchPizzaVenues(getActivity(), getLastLatitude(),
                        getLastLongitude(), adapter.getItemCount());
                venueFetching = true;
                adapter.showProgressBar();
                adapter.notifyDataSetChanged();
            }
        }
    };

    private double getLastLatitude() {
        if (selectedLocation == CENTER_OF_NY) {
            return centerOfNY.getLatitude();
        }
        return ((MainActivity) getActivity()).getLastLatitude();
    }

    private double getLastLongitude() {
        if (selectedLocation == CENTER_OF_NY) {
            return centerOfNY.getLongitude();
        }
        return ((MainActivity) getActivity()).getLastLongitude();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    enum SelectedLocation {
        MY_LOCATION, CENTER_OF_NY
    }
}
