package com.tech.quiz.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.tech.R;
import com.tech.quiz.presenter.DiscussionPresenterImp;
import com.tech.quiz.view.views.DiscussionView;

import library.basecontroller.AppBaseCompatActivity;

public class DiscussionActivity extends AppBaseCompatActivity<DiscussionPresenterImp> implements DiscussionView {

    private FrameLayout progressBar;

    /**
     * In child fragment you must provide presenter implementation to this,
     * otherwise it will give a null pointer exception
     *
     * @return return the presenterImp instance
     */
    @Override
    protected DiscussionPresenterImp createPresenter() {
        return new DiscussionPresenterImp(this, this);
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

        progressBar = (FrameLayout) findViewById(R.id.progressBarContainer);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(getPresenter().initAdapter());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_discussion);
        super.onCreate(savedInstanceState);
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

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        // show snackbar here
    }
}
