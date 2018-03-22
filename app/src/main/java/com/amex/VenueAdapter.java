package com.amex;

        import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueAdapterViewHolder> {

    private ArrayList<Venue> mVenues;
    private Context mContext;
    private final VenueAdapterOnClickHandler mClickHandler;

    public VenueAdapter(Context context, VenueAdapterOnClickHandler handler) {
        mContext = context;
        mClickHandler = handler;
    }

    @Override
    public VenueAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Gets context of the parent view group
        Context context = parent.getContext();

        // Specifies whether the viewHolder is directly attached to paren
        final boolean attachDirectlyToParent = false;

        // Gets the id for the list item
        int layoutIdForListItem = R.layout.venue_list_item;

        // Gets activity_recipe_detail Layout inflater to inflate the ViewHolder
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflates the layout for the ViewHolder
        View createdView = inflater.inflate(layoutIdForListItem, parent, attachDirectlyToParent);

        // Returns the created ViewHolder
        return new VenueAdapterViewHolder(createdView);
    }

    @Override
    public void onBindViewHolder(VenueAdapterViewHolder holder, int position) {


        Venue currentVenue = mVenues.get(position);
        // Sets the title of the movie in its TextView
        holder.mTitleTextView.setText(currentVenue.getmAddress());

        holder.mSubtitleTextView.setVisibility(View.VISIBLE);
        holder.mSubtitleTextView.setText(currentVenue.getmOffer());
        if (currentVenue.getmOffer().isEmpty()){
            holder.mSubtitleTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mVenues == null ? 0 : mVenues.size();
    }

    public interface VenueAdapterOnClickHandler {
        void onClick(Venue selectedVenue);
    }

    public class VenueAdapterViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{

        @BindView(R.id.tv_title)
        TextView mTitleTextView;
        @BindView(R.id.tv_subtitle)
        TextView mSubtitleTextView;


        public VenueAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Venue selectedVenue = mVenues.get(adapterPosition);
            mClickHandler.onClick(selectedVenue);

        }
    }

    public void setVenueData(ArrayList<Venue> venueData) {

        mVenues = venueData;
        // Notifies any registered observers that the data set has changed
        notifyDataSetChanged();
    }
}