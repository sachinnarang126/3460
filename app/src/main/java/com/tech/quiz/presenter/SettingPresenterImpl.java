package com.tech.quiz.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.Preference;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;

import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.repositories.presenter.SettingPresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.views.SettingView;

import library.mvp.FragmentPresenter;
import library.mvp.IBaseInterActor;

/**
 * @author Sachin Narang
 */

public class SettingPresenterImpl extends FragmentPresenter<SettingView, IBaseInterActor> implements SettingPresenter,
        Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    private boolean hasToUpdateHomeScreen;

    public SettingPresenterImpl(SettingView view, Context context) {
        super(view, context);
    }

    @Override
    public void showResetAllQuestionDialog(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void resetAllQuestion() {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(getContext());
        databaseManager.initDefaultValueToAllQuestion();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "prefResetAll":
                showResetAllQuestionDialog("Do you want to reset all questions?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hasToUpdateHomeScreen = true;
                        resetAllQuestion();
                        getView().showSnackBar("Question reset successfully");
                    }
                });
                break;
        }
        return true;
    }

    @Override
    protected IBaseInterActor createInterActor() {
        return null;
    }

    /**
     * Called when a Preference has been changed by the user. This is
     * called before the state of the Preference is about to be updated and
     * before the state is persisted.
     *
     * @param preference The changed Preference.
     * @param newValue   The new value of the Preference.
     * @return True to update the state of the Preference with the new value.
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {

            case "prefShowAnsweredQuestion":
                hasToUpdateHomeScreen = true;
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        if (hasToUpdateHomeScreen)
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(Constant.CATEGORY_RECEIVER));
        super.onDestroy();
    }
}
