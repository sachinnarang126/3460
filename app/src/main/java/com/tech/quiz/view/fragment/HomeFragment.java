package com.tech.quiz.view.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.tech.R;
import com.tech.quiz.basecontroller.AppCompatFragment;
import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.presenter.HomePresenterImpl;
import com.tech.quiz.repositories.presenter.HomePresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.HomeActivity;
import com.tech.quiz.view.activity.SettingsActivity;
import com.tech.quiz.view.activity.SubscriptionDataActivity;
import com.tech.quiz.view.views.HomeView;

import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends AppCompatFragment implements View.OnClickListener, HomeView {

    private HomePresenter homePresenter;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getActivity().setTitle("Tech Quiz");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeakReference<HomeView> weakReference = new WeakReference<HomeView>(this);
        homePresenter = new HomePresenterImpl(weakReference);
        homePresenter.prepareToFetchQuestion();

        if (!((HomeActivity) getActivity()).isSubscribedUser())
            MobileAds.initialize(getActivity().getApplicationContext(), getString(R.string.home_footer));
    }

    @Override
    public void onDestroy() {
        homePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        if (!((HomeActivity) getActivity()).isSubscribedUser()) {

            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder()/*
                    .addTestDevice("9210683FFFBDE1953CE613AB2FDE46E5").
                            addTestDevice("F56162DD974939BBF71A8D3E8CC8A44A").
                            addTestDevice("1FBF7D7CF19C0C11158AF44FDA595121").
                            addTestDevice("F58DA099F52C8D53E4DD635D0C5EB709")*/.build();

            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }


        TextView txtAndroid = (TextView) view.findViewById(R.id.txtAndroid);
        TextView txtJava = (TextView) view.findViewById(R.id.txtJava);
        TextView txtIos = (TextView) view.findViewById(R.id.txtIos);

        TextView txtAndroidCount = (TextView) view.findViewById(R.id.txtAndroidCount);
        TextView txtJavaCount = (TextView) view.findViewById(R.id.txtJavaCount);
        TextView txtIosCount = (TextView) view.findViewById(R.id.txtIosCount);

        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(getActivity());
        String androidQuestionText = "Total: " + databaseManager.fetchCountOfAllAndroidQuestion() + ", Answered: " + databaseManager.getAnsweredAndroidQuestionCount();
        String iosQuestionText = "Total: " + databaseManager.fetchCountOfAllIosQuestion() + ", Answered: " + databaseManager.getAnsweredIosQuestionCount();
        String javaQuestionText = "Total: " + databaseManager.fetchCountOfAllJavaQuestion() + ", Answered: " + databaseManager.getAnsweredJavaQuestionCount();

        txtAndroidCount.setText(androidQuestionText);
        txtIosCount.setText(iosQuestionText);
        txtJavaCount.setText(javaQuestionText);

        txtAndroid.setOnClickListener(this);
        txtJava.setOnClickListener(this);
        txtIos.setOnClickListener(this);

        setAnimationView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (((HomeActivity) getActivity()).isSubscribedUser()) {
            menu.findItem(R.id.action_add_free).setVisible(false);
        } else {
            menu.findItem(R.id.action_add_free).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.action_add_free:
                startActivity(new Intent(getActivity(), SubscriptionDataActivity.class));
                //((HomeActivity) getActivity()).showSnackBar("You will get the add free version on next update");
                break;

            case R.id.action_rate_us:
                ((HomeActivity) getActivity()).openPlayStoreForRating("com.tech.quiz");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int serviceType = 0;
        String technology = "";
        switch (view.getId()) {
            case R.id.txtAndroid:
                serviceType = Constant.ANDROID;
                technology = getString(R.string.android);
                break;

            case R.id.txtIos:
                serviceType = Constant.IOS;
                technology = getString(R.string.ios);
                break;

            case R.id.txtJava:
                serviceType = Constant.JAVA;
                technology = getString(R.string.java);
                break;

        }
        ((HomeActivity) getActivity()).startFragmentTransactionAllowingBackStack(CategoryFragment.getInstance(technology, serviceType),
                getString(R.string.category_fragment), R.id.fragment_container);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess() {

    }

    public void setAnimationView() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scale = ObjectAnimator.ofFloat(getView().findViewById(R.id.androidContainer), "scaleX", 0.0f, 1.0f);
        scale.setDuration(200);
        animatorSet.play(scale);
        animatorSet.start();

        AnimatorSet animatorSet1 = new AnimatorSet();
        ObjectAnimator scale2 = ObjectAnimator.ofFloat(getView().findViewById(R.id.iosContainer), "scaleX", 0.0f, 1.0f);
        scale2.setDuration(350);
        animatorSet1.play(scale2);
        animatorSet1.start();

        AnimatorSet animatorSet2 = new AnimatorSet();
        ObjectAnimator scale3 = ObjectAnimator.ofFloat(getView().findViewById(R.id.javaContainer), "scaleX", 0.0f, 1.0f);
        scale3.setDuration(500);
        animatorSet2.play(scale3);
        animatorSet2.start();

//        animatorSet.play(scale).before(childSet);
//        childSet.play(scale2).with(scale3);
//        animatorSet.start();
    }
}
