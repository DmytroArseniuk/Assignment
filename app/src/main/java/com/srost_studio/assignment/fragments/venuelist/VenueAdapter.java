package com.srost_studio.assignment.fragments.venuelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.srost_studio.assignment.R;
import com.srost_studio.assignment.fragments.venuelist.elements.ProgressBarElement;
import com.srost_studio.assignment.fragments.venuelist.elements.VenueElement;
import com.srost_studio.assignment.fragments.venuelist.elements.VenueListElement;
import com.srost_studio.assignment.fragments.venuelist.elements.ViewElements;
import com.srost_studio.assignment.fragments.venuelist.viewholders.ProgressBarViewHolder;
import com.srost_studio.assignment.fragments.venuelist.viewholders.VenueViewHolder;
import com.srost_studio.assignment.fragments.venuelist.viewholders.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import br.com.condesales.models.Venue;

import static com.srost_studio.assignment.fragments.venuelist.elements.ViewElements.PROGRESS_BAR;

public class VenueAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ProgressBarElement progressbar = new ProgressBarElement();

    private List<VenueListElement> elements;

    public VenueAdapter(List<Venue> elements) {
        this.elements = convertVenues(elements);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (ViewElements.values()[viewType]) {
            case ELEMENT:
                return new VenueViewHolder(inflater.inflate(R.layout.list_item_venue, parent, false));
            case PROGRESS_BAR:
                return new ProgressBarViewHolder(inflater.inflate(R.layout.list_item_progressbar, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        elements.get(position).bind(holder);
    }

    public List<VenueListElement> convertVenues(List<Venue> venues) {
        ArrayList<VenueListElement> elements = new ArrayList<>();
        for (Venue venue : venues) {
            elements.add(new VenueElement(venue));
        }
        return elements;
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    @Override
    public int getItemViewType(int position) {
        return elements.get(position).getElementType();
    }

    public void hideProgressBar() {
        elements.remove(progressbar);
    }

    public void showProgressBar() {
        elements.add(progressbar);
    }

    public boolean progressbarShown(){
        return elements.contains(progressbar);
    }

    public void appendVenues(List<Venue> additionalVenues) {
        if(progressbarShown()) {
            hideProgressBar();
        }
        elements.addAll(convertVenues(additionalVenues));
        showProgressBar();
    }
}
