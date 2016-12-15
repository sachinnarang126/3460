package com.tech.quiz.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;

import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.repositories.presenter.SettingPresenter;
import com.tech.quiz.view.views.SettingView;

import library.mvp.BasePresenter;

/**
 * @author Sachin Narang
 */

public class SettingPresenterImpl extends BasePresenter<SettingView> implements SettingPresenter, Preference.OnPreferenceClickListener {

    public SettingPresenterImpl(SettingView view, Context context) {
        attachView(view, context);
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
                        resetAllQuestion();
                        getView().showSnackBar("Question reset successfully");
                    }
                });
                break;
        }
        return false;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        detachView();
    }
}
