package com.tech.quiz.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tech.R;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.presenter.SplashPresenterImpl;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.views.SplashView;

import library.basecontroller.AppBaseCompatActivity;

/**
 * @author Sachin Narang
 */

public class SplashActivity extends AppBaseCompatActivity<SplashPresenterImpl> implements SplashView {

    private ProgressBar progressBar;

    @Override
    protected SplashPresenterImpl createPresenter() {
        return new SplashPresenterImpl(this, this);
    }

    @Override
    protected void initUI() {
        if (DataHolder.getInstance().getPreferences(this).
                getBoolean(Constant.IS_APP_FIRST_LAUNCH, true)) {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            // fetch all data from server and save it to local DB
            getPresenter().prepareToFetchQuestion();
        } else {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        showSnackBar(error);
        closeApplication();
    }

    private void closeApplication() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

    private void showSnackBar(String error) {
        Snackbar.make(findViewById(R.id.container), error, Snackbar.LENGTH_LONG).show();
    }
}
