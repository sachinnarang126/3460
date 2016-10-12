package com.interviewquestion.fragment;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.interviewquestion.R;
import com.interviewquestion.activity.SettingsActivity;
import com.interviewquestion.presenter.SettingPresenter;
import com.interviewquestion.presenter.SettingPresenterImpl;
import com.interviewquestion.view.SettingView;

import java.lang.ref.WeakReference;

public class SettingsFragment extends PreferenceFragment implements SettingView/*, SharedPreferences.OnSharedPreferenceChangeListener*/ {

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
        Preference prefResetAll = findPreference("prefShowAnsweredQuestion");
        prefResetAll.setOnPreferenceClickListener((SettingPresenterImpl) settingPresenter);
    }

    @Override
    public void showSnackBar(String text) {
        ((SettingsActivity) getActivity()).showSnackBar(text);
    }

    /*@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }*/
}
