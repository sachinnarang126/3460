package com.interviewquestion.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.interviewquestion.R;
import com.interviewquestion.basecontroller.AppBaseCompatActivity;
import com.interviewquestion.presenter.QuestionPresenter;
import com.interviewquestion.presenter.QuestionPresenterImpl;
import com.interviewquestion.view.QuestionView;

import java.lang.ref.WeakReference;

public class QuestionActivity extends AppBaseCompatActivity implements QuestionView {

    private QuestionPresenter questionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("title"));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        WeakReference<QuestionView> reference = new WeakReference<QuestionView>(this);
        questionPresenter = new QuestionPresenterImpl(reference);

        showProgress();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        questionPresenter.onCreate();
        viewPager.setAdapter(questionPresenter.initAdapter());
        hideProgress();
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
                getSupportActionBar().setSubtitle(getString(R.string.answered));
                questionPresenter.prepareListToShowAnsweredQuestion();
                break;

            case R.id.action_show_unanswered:
                getSupportActionBar().setSubtitle(getString(R.string.unanswered));
                questionPresenter.prepareListToShowUnansweredQuestion();
                break;

            case R.id.action_show_all:
                getSupportActionBar().setSubtitle(getString(R.string.all));
                questionPresenter.prepareListToShowAllQuestion();
                break;

            case R.id.action_reset_all:
                getSupportActionBar().setSubtitle(getString(R.string.reset));
                questionPresenter.prepareListToResetAll();
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
        questionPresenter.onDestroy();
    }
}
