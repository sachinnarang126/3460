package com.tech.quiz.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.tech.R;
import com.tech.quiz.view.fragment.DiscussionFragment;

import library.basecontroller.AppBaseCompatActivity;
import library.mvp.ActivityPresenter;

public class DiscussionActivity extends AppBaseCompatActivity {


    /**
     * In child fragment you must provide presenter implementation to this,
     * otherwise it will give a null pointer exception
     *
     * @return return the presenterImp instance
     */
    @Override
    protected ActivityPresenter createPresenter() {
        return null;
    }

    /**
     * initialized the ui component
     */
    @Override
    protected void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        AdView mAdView = (AdView) findViewById(R.id.adView);
        if (!isSubscribedUser()) {
            MobileAds.initialize(getApplicationContext(), getString(R.string.hot_topics));
            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = getAddRequest();
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_discussion);
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, DiscussionFragment.getInstance())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
