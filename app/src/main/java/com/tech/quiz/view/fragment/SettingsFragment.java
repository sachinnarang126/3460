package com.tech.quiz.view.fragment;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.tech.R;
import com.tech.quiz.presenter.SettingPresenterImpl;
import com.tech.quiz.repositories.presenter.SettingPresenter;
import com.tech.quiz.view.activity.SettingsActivity;
import com.tech.quiz.view.views.SettingView;

import java.lang.ref.WeakReference;

public class SettingsFragment extends PreferenceFragment implements SettingView {

    public static SettingsFragment getInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SwitchPreference prefRemoveQuestion = (SwitchPreference) findPreference("prefRemoveQuestion");
        addPreferencesFromResource(R.xml.settings);

        WeakReference<SettingView> weakReference = new WeakReference<SettingView>(this);
        SettingPresenter settingPresenter = new SettingPresenterImpl(weakReference);
        Preference prefResetAll = findPreference("prefResetAll");
        prefResetAll.setOnPreferenceClickListener((SettingPresenterImpl) settingPresenter);

        final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.settings_interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("9210683FFFBDE1953CE613AB2FDE46E5").
                        addTestDevice("F56162DD974939BBF71A8D3E8CC8A44A").
                        addTestDevice("F58DA099F52C8D53E4DD635D0C5EB709").build();

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

    @Override
    public void showSnackBar(String text) {
        ((SettingsActivity) getActivity()).showSnackBar(text);
    }
}