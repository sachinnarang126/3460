package com.tech.quiz.repositories.presenter;

import android.content.DialogInterface;

public interface SettingPresenter {

    void showResetAllQuestionDialog(String message, DialogInterface.OnClickListener okListener);

    void resetAllQuestion();
}
