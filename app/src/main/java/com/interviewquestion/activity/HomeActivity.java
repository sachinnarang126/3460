package com.interviewquestion.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.interviewquestion.R;
import com.interviewquestion.basecontroller.AppBaseCompatActivity;
import com.interviewquestion.fragment.HomeFragment;

public class HomeActivity extends AppBaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                // fragmentTransaction.addToBackStack(tag);
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

}
