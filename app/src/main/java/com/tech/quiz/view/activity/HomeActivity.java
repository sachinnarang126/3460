package com.tech.quiz.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tech.R;
import com.tech.quiz.adapter.PagerAdapter;
import com.tech.quiz.dataholder.DataHolder;

import library.basecontroller.AppBaseCompatActivity;
import library.mvp.ActivityPresenter;

/**
 * @author Sachin Narang
 */

public class HomeActivity extends AppBaseCompatActivity {

    private boolean doubleBackToExitPressedOnce;

    @Override
    protected ActivityPresenter createPresenter() {
        return null;
    }

    @Override
    protected void initUI() {
        DataHolder.getInstance().setHomeActivityInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //loadHomeFragment();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), HomeActivity.this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        /*for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null)
                tab.setCustomView(pagerAdapter.getTabView(i));
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_new);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isSubscribedUser()) {
            menu.findItem(R.id.action_add_free).setVisible(false);
        } else {
            menu.findItem(R.id.action_add_free).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.action_add_free:
                startActivity(new Intent(this, SubscriptionDataActivity.class));
                break;

            case R.id.action_rate_us:
                openPlayStoreForRating("com.tech.quiz");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void loadHomeFragment() {
        startFragmentTransaction(HomeFragment.getInstance(), getString(R.string.home_fragment), R.id.fragment_container);
    }*/

    /*public void startFragmentTransaction(Fragment fragment, String tag, int container) {
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
    }*/

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
        System.out.println("getSupportFragmentManager().getBackStackEntryCount() " + getSupportFragmentManager().getBackStackEntryCount());
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
        Snackbar.make(findViewById(R.id.viewpager), text, 2000).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataHolder.getInstance().setHomeActivityInstance(null);
    }
}
