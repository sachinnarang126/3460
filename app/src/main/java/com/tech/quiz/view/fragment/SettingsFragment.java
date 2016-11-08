package com.tech.quiz.view.fragment;


import android.os.Bundle;
import android.preference.Preference;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.tech.R;
import com.tech.quiz.presenter.SettingPresenterImpl;
import com.tech.quiz.view.activity.SettingsActivity;
import com.tech.quiz.view.views.SettingView;

import library.basecontroller.PreferenceCompatFragment;

public class SettingsFragment extends PreferenceCompatFragment<SettingPresenterImpl> implements SettingView {

    public static SettingsFragment getInstance() {
        return new SettingsFragment();
    }

    @Override
    protected SettingPresenterImpl createPresenter() {
        return new SettingPresenterImpl();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SwitchPreference prefRemoveQuestion = (SwitchPreference) findPreference("prefRemoveQuestion");
        addPreferencesFromResource(R.xml.settings);

        Preference prefResetAll = findPreference("prefResetAll");
        prefResetAll.setOnPreferenceClickListener(getPresenter());

        final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
        if (!((SettingsActivity) getActivity()).isSubscribedUser()) {
            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.settings_interstitial_full_screen));

            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("9210683FFFBDE1953CE613AB2FDE46E5").
                            addTestDevice("F56162DD974939BBF71A8D3E8CC8A44A").
                            addTestDevice("1FBF7D7CF19C0C11158AF44FDA595121").
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
    }

    @Override
    public void showSnackBar(String text) {
        ((SettingsActivity) getActivity()).showSnackBar(text);
    }
}
