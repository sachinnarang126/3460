package com.tech.quiz.repositories.presenter;

import android.content.DialogInterface;

/**
 * @author Sachin Narang
 */

public interface SettingPresenter {

    void showResetAllQuestionDialog(String message, DialogInterface.OnClickListener okListener);

    void resetAllQuestion();
}
