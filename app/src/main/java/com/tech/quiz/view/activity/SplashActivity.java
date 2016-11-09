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
import com.tech.quiz.basecontroller.AppBaseCompatActivity;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.presenter.SplashPresenterImpl;
import com.tech.quiz.repositories.presenter.SplashPresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.views.SplashView;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppBaseCompatActivity implements SplashView {

    private ProgressBar progressBar;
    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        WeakReference<SplashView> splashViewWeakReference = new WeakReference<SplashView>(this);
        splashPresenter = new SplashPresenterImpl(splashViewWeakReference);

        if (DataHolder.getInstance().getPreferences(this).
                getBoolean(Constant.IS_APP_FIRST_LAUNCH, true)) {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            // fetch all data from server and save it to local DB
            splashPresenter.prepareToFetchQuestion();
        } else {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }
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

    @Override
    protected void onDestroy() {
        splashPresenter.onDestroy();
        super.onDestroy();
    }

    private void showSnackBar(String error) {
        Snackbar.make(findViewById(R.id.container), error, Snackbar.LENGTH_LONG).show();
    }
}
