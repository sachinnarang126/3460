package com.tech.quiz.view.fragment;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.tech.R;
import com.tech.quiz.presenter.SettingPresenterImpl;
import com.tech.quiz.view.activity.SettingsActivity;
import com.tech.quiz.view.views.SettingView;

import library.basecontroller.PreferenceCompatFragment;

/**
 * @author Sachin Narang
 */

public class SettingsFragment extends PreferenceCompatFragment<SettingPresenterImpl> implements SettingView {

    public static SettingsFragment getInstance() {
        return new SettingsFragment();
    }

    @Override
    protected SettingPresenterImpl onAttachPresenter() {
        return new SettingPresenterImpl(this, getActivity());
    }

    @Override
    protected void initUI(View view) {
        final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
        if (!((SettingsActivity) getActivity()).isSubscribedUser()) {
            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.settings_interstitial_full_screen));

            AdRequest adRequest = ((SettingsActivity) getActivity()).getAddRequest();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(null);
        addPreferencesFromResource(R.xml.settings);
        Preference prefResetAll = findPreference("prefResetAll");
        SwitchPreference prefShowAnsweredQuestion = (SwitchPreference) findPreference("prefShowAnsweredQuestion");
        prefShowAnsweredQuestion.setOnPreferenceChangeListener(getPresenter());
        prefResetAll.setOnPreferenceClickListener(getPresenter());
    }

    @Override
    public void showSnackBar(String text) {
        ((SettingsActivity) getActivity()).showSnackBar(text);
    }
}
