package com.tech.quiz.view.activity;

import android.os.Bundle;
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

/**
 * @author Sachin Narang
 */

public class QuestionActivity extends AppBaseCompatActivity<QuestionPresenterImpl> implements QuestionView {

    private ViewPager viewPager;

    @Override
    protected QuestionPresenterImpl createPresenter() {
        return new QuestionPresenterImpl(this, this);
    }

    @Override
    protected void initUI() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        if (!isSubscribedUser()) {
            MobileAds.initialize(getApplicationContext(), getString(R.string.question_footer));

            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = getAddRequest();
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

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());

        if (getIntent().getBooleanExtra("isQuizMode", false)) {
            viewPager.setAdapter(getPresenter().initAdapter());
        } else {
            viewPager.setAdapter(getPresenter().initAdapter(getIntent().getIntExtra("technology", 0)));
        }

        getPresenter().shuffleQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_question);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (getIntent().getBooleanExtra("isQuizMode", false)) {
            MenuItem menuItem = menu.findItem(R.id.action_result);
            menuItem.setVisible(true);
        } else menu.findItem(R.id.action_result).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
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

            case R.id.action_result:
                getPresenter().showResult();
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
    public String getTechnology() {
        switch (getIntent().getIntExtra("technology", 0)) {
            case Constant.ANDROID:
                return getString(R.string.android);

            case Constant.IOS:
                return getString(R.string.ios);

            case Constant.JAVA:
                return getString(R.string.java);

            default:
                return "";
        }
    }

    @Override
    public void setSelectionOfViewPager(int position) {
        viewPager.setCurrentItem(position, true);
    }
}
