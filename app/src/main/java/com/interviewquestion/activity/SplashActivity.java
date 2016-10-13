package com.interviewquestion.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.interviewquestion.R;
import com.interviewquestion.basecontroller.AppBaseCompatActivity;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.presenter.SplashPresenterImpl;
import com.interviewquestion.repositories.presenter.SplashPresenter;
import com.interviewquestion.util.Constant;
import com.interviewquestion.view.SplashView;

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
