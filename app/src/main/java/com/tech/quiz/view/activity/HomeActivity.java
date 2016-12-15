package com.tech.quiz.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.tech.R;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.view.fragment.HomeFragment;

import library.basecontroller.AppBaseCompatActivity;
import library.mvp.BasePresenter;

public class HomeActivity extends AppBaseCompatActivity {

    private boolean doubleBackToExitPressedOnce;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataHolder.getInstance().setHomeActivityInstance(this);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadHomeFragment();
    }

    private void loadHomeFragment() {
        startFragmentTransaction(HomeFragment.getInstance(), getString(R.string.home_fragment), R.id.fragment_container);
    }

    public void startFragmentTransaction(Fragment fragment, String tag, int container) {
        try {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            Fragment fragmentFromBackStack = mFragmentManager.findFragmentByTag(tag);
            if (fragmentFromBackStack == null) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(container, fragment, tag);
                fragmentTransaction.commit();
            } else {
                // this called if add to back stack
                mFragmentManager.popBackStack(tag, 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startFragmentTransactionAllowingBackStack(Fragment fragment, String tag, int container) {
        try {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            Fragment fragmentFromBackStack = mFragmentManager.findFragmentByTag(tag);
            if (fragmentFromBackStack == null) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(container, fragment, tag);
                fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commit();
            } else {
                // this called if add to back stack
                mFragmentManager.popBackStack(tag, 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            doubleBackToExitPressedOnce = true;
            showSnackBar("Please press BACK again to exit");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

    public void showSnackBar(String text) {
        Snackbar.make(findViewById(R.id.fragment_container), text, 2000).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataHolder.getInstance().setHomeActivityInstance(null);
    }
}
