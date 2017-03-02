package com.tech.quiz.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.tech.R;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.presenter.HomePresenterImpl;
import com.tech.quiz.view.views.HomeView;

import library.basecontroller.AppBaseCompatActivity;

/**
 * @author Sachin Narang
 */

public class HomeActivity extends AppBaseCompatActivity<HomePresenterImpl> implements HomeView {

    private boolean doubleBackToExitPressedOnce;
    private Toolbar toolbar;
    private Menu mMenu;

    @Override
    protected HomePresenterImpl createPresenter() {
        return new HomePresenterImpl(this, this);
    }

    @Override
    protected void initUI() {

        AdView mAdView = (AdView) findViewById(R.id.adView);
        if (!isSubscribedUser()) {
            MobileAds.initialize(getApplicationContext(), getString(R.string.home_footer));
            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("9210683FFFBDE1953CE613AB2FDE46E5").
                            addTestDevice("F56162DD974939BBF71A8D3E8CC8A44A").
                            addTestDevice("1FBF7D7CF19C0C11158AF44FDA595121").
                            addTestDevice("F58DA099F52C8D53E4DD635D0C5EB709").build();
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }

        DataHolder.getInstance().setHomeActivityInstance(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(getPresenter().initAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(getPresenter());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        mMenu = menu;
        // Retrieve the SearchView and plug it into SearchManager
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);

        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        searchEditText.setHintTextColor(ContextCompat.getColor(this, android.R.color.white));

        searchView.setOnQueryTextListener(getPresenter());
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItemCompat.setOnActionExpandListener(searchMenu, getPresenter());

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

            case R.id.action_ask_question:
                startActivity(new Intent(this, AskQuestionActivity.class));
                break;

            case R.id.action_discussion:
                startActivity(new Intent(this, DiscussionActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private void showSnackBar(String text) {
        Snackbar.make(findViewById(R.id.viewPager), text, 2000).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataHolder.getInstance().setHomeActivityInstance(null);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public Menu getOptionMenu() {
        return mMenu;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }
}
