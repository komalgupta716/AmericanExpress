package com.amex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {


    private ArrayList<Venue> mVenues;
    private Venue mVenue;
    @BindView(R.id.tv_detail_subtitle)
    TextView mSubtitleTextView;
    @BindView(R.id.tv_detail_title)
    TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        AmexApp appState = (AmexApp) getApplicationContext();
        mVenues = appState.mVenues;


        Log.d("DDF","onCreate");

        int id = getIntent().getIntExtra("VENUE",0);

        mVenue = mVenues.get(id);
        mTitleTextView.setText(mVenue.getmAddress());
        mSubtitleTextView.setText(mVenue.getmOffer());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("DDF","on New Intent");
        super.onNewIntent(intent);
        int id = intent.getIntExtra("VENUE",0);

        mVenue = mVenues.get(id);
        mTitleTextView.setText(mVenue.getmAddress());
        mSubtitleTextView.setText(mVenue.getmOffer());

    }
}
