package com.amex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VenuesActivity extends AppCompatActivity implements VenueAdapter.VenueAdapterOnClickHandler {


    @BindView(R.id.rv_venues)
    RecyclerView mRecyclerView;

    private VenueAdapter mAdapter;
    private ArrayList<Venue> mVenues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);

        ButterKnife.bind(this);

        mAdapter = new VenueAdapter(this, this);

        mVenues = getIntent().getParcelableArrayListExtra("VENUES");

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setVenueData(mVenues);

    }

    @Override
    public void onClick(Venue selectedVenue) {
        Intent intent = new Intent(VenuesActivity.this, DetailActivity.class);
        intent.putExtra("VENUE", selectedVenue.getmId());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
