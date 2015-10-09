package com.srost_studio.assignment.fragments.venuedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.srost_studio.assignment.MainApplication;
import com.srost_studio.assignment.R;
import com.srost_studio.assignment.services.VenueService;
import com.srost_studio.assignment.util.DistanceUtil;
import com.srost_studio.assignment.util.EventBus;

import br.com.condesales.models.Venue;

public class VenueDetailsFragment extends Fragment{

    public static final String FRAGMENT_NAME = VenueDetailsFragment.class.getName();
    public static final String VENUE_ID = "venue_id";

    private String venueId;
    private Venue venue;
    private TextView name;
    private TextView distance;
    private TextView rating;
    private ImageView photo;



    public static VenueDetailsFragment newInstance(String venueId) {
        final VenueDetailsFragment fragment = new VenueDetailsFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(VENUE_ID, venueId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        venueId = getArguments().getString(VENUE_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_venue_details, container, false);
        name = (TextView) v.findViewById(R.id.venue_name);
        distance = (TextView) v.findViewById(R.id.venue_distance);
        rating = (TextView) v.findViewById(R.id.venue_rating);
        photo = (ImageView) v.findViewById(R.id.venue_photo);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);
        findVenue();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);
    }

    private void findVenue() {
        ((MainApplication)getActivity().getApplication()).getVenueService().findVenue(venueId,
                new VenueService.FindVenueCallback() {
                    @Override
                    public void onPostExecute(Venue venue) {
                        VenueDetailsFragment.this.venue = venue;
                        initUI();
                    }
                });
    }

    private void initUI(){
        name.setText(venue.getName());
        distance.setText(DistanceUtil.getDisplayableDistance(venue.getLocation().getDistance()));
        if(venue.getRating() != 0) {
            rating.setText(String.valueOf(venue.getRating()));
        }
    }
}
