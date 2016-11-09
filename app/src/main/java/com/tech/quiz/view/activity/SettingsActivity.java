package com.tech.quiz.view.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tech.R;
import com.tech.quiz.view.fragment.SettingsFragment;

import library.basecontroller.AppBaseCompatActivity;
import library.mvp.MvpBasePresenter;

public class SettingsActivity extends AppBaseCompatActivity {

    @Override
    protected MvpBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment.getInstance())
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

    public void showSnackBar(String text) {
        Snackbar.make(findViewById(R.id.container), text, Snackbar.LENGTH_LONG).show();
    }
}
