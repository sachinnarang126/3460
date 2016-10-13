package com.interviewquestion.presenter;

import android.content.DialogInterface;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;

import com.interviewquestion.databasemanager.DatabaseManager;
import com.interviewquestion.fragment.SettingsFragment;
import com.interviewquestion.repositories.presenter.SettingPresenter;
import com.interviewquestion.view.SettingView;

import java.lang.ref.WeakReference;

/**
 * Created by root on 12/10/16.
 */

public class SettingPresenterImpl implements SettingPresenter, Preference.OnPreferenceClickListener {

    private WeakReference<SettingView> settingView;

    public SettingPresenterImpl(WeakReference<SettingView> settingView) {
        this.settingView = settingView;
    }

    @Override
    public void showResetAllQuestionDialog(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(((SettingsFragment) settingView.get()).getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void resetAllQuestion() {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(((SettingsFragment) settingView.get()).getActivity());
        databaseManager.initDefaultValueToAllQuestion();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        System.out.println("SettingPresenterImpl.onPreferenceClick");
        switch (preference.getKey()) {

            case "prefResetAll":
                showResetAllQuestionDialog("Do you want to reset all questions?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetAllQuestion();
                        settingView.get().showSnackBar("Question reset successfully");
                    }
                });
                break;
        }
        return false;
    }
}
