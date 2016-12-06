package com.tech.quiz.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.tech.R;
import com.tech.quiz.presenter.QuestionPresenterImpl;
import com.tech.quiz.util.Constant;
import com.tech.quiz.util.DepthPageTransformer;
import com.tech.quiz.view.views.QuestionView;

import library.basecontroller.AppBaseCompatActivity;

public class QuestionActivity extends AppBaseCompatActivity<QuestionPresenterImpl> implements QuestionView {

    @Override
    protected QuestionPresenterImpl createPresenter() {
        return new QuestionPresenterImpl(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        if (!isSubscribedUser()) {
            MobileAds.initialize(getApplicationContext(), getString(R.string.question_footer));

            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("9210683FFFBDE1953CE613AB2FDE46E5").
                            addTestDevice("F56162DD974939BBF71A8D3E8CC8A44A").
                            addTestDevice("1FBF7D7CF19C0C11158AF44FDA595121").
                            addTestDevice("F58DA099F52C8D53E4DD635D0C5EB709").build();
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("title"));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setAdapter(getPresenter().initAdapter(getIntent().getIntExtra("technology", 0)));
        getPresenter().shuffleQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_show_answered:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setSubtitle(getString(R.string.answered));
                getPresenter().prepareListToShowAnsweredQuestion();
                break;

            case R.id.action_show_unanswered:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setSubtitle(getString(R.string.unanswered));
                getPresenter().prepareListToShowUnansweredQuestion();
                break;

            case R.id.action_show_all:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setSubtitle(getString(R.string.all));
                getPresenter().prepareListToShowAllQuestion();
                break;

            case R.id.action_reset_all:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setSubtitle(getString(R.string.reset));
                getPresenter().prepareListToResetAll();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.CATEGORY_RECEIVER));
    }
}
