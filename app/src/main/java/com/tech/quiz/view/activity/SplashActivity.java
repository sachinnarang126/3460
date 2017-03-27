package com.tech.quiz.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
//        makeAppAddFree();
        sampleForXiaomi();
        if (DataHolder.getInstance().getPreferences(this).
                getBoolean(Constant.IS_APP_FIRST_LAUNCH, true)) {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            // fetch all data from server and save it to local DB
            getPresenter().prepareToFetchQuestion();
        } else {
            /*startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();*/
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

    /*private void makeAppAddFree() {
        if (!isSubscribedUser()) {
            DataHolder.getInstance().getPreferences(this).edit().putBoolean(Constant.IS_SUBSCRIBED_USER, true).apply();
        }
    }*/


    private void sampleForXiaomi() {
        String brand = Build.BRAND;
        System.out.println("------" + brand + "--------");
        if (brand.contains("Xiaomi")) {
            showDialog("FinArt required permission to read your SMS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 102);
                }
            }, false);
        } else {
            showToast("Non Xiaomi Device");
        }
    }

    public void showDialog(String message, DialogInterface.OnClickListener okListener, boolean hasToShowDone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Enable", okListener)
                .setCancelable(false);
        if (hasToShowDone)
            builder.setNegativeButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 102:
                showDialog("FinArt required permission to read your SMS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 102);
                    }
                }, true);
                break;
        }
    }
}
