package com.tech.quiz.view.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.tech.R;
import com.tech.quiz.presenter.AskQuestionPresenterImpl;
import com.tech.quiz.view.views.AskQuestionView;

import library.basecontroller.AppBaseCompatActivity;

public class AskQuestionActivity extends AppBaseCompatActivity<AskQuestionPresenterImpl> implements AskQuestionView, View.OnClickListener {

    private EditText etTechnology, etEmail, etQuestion;

    /**
     * In child Activity you must provide presenter implementation to this,
     * otherwise it will give a null pointer exception
     *
     * @return return the presenterImp instance
     */
    @Override
    protected AskQuestionPresenterImpl createPresenter() {
        return new AskQuestionPresenterImpl(this, this);
    }

    /**
     * initialized the ui component
     */
    @Override
    protected void initUI() {

        final InterstitialAd mInterstitialAd = new InterstitialAd(this);
        if (!isSubscribedUser()) {
            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.settings_interstitial_full_screen));

            AdRequest adRequest = getAddRequest();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });
        }

        etTechnology = (EditText) findViewById(R.id.etTechnology);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etQuestion = (EditText) findViewById(R.id.etQuestion);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ask_question);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onError(String error) {
        Snackbar.make(findViewById(R.id.content_ask_question), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void resetToDefaultValue() {
        etTechnology.setText("");
        etEmail.setText("");
        etQuestion.setText("");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                getPresenter().createAskQuestionApi(etTechnology.getText().toString().trim(),
                        etEmail.getText().toString().trim(), etQuestion.getText().toString().trim());
                break;
        }
    }
}
