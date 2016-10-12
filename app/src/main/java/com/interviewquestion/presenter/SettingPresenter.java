package com.interviewquestion.presenter;

import android.content.DialogInterface;

/**
 * Created by root on 28/9/16.
 */

public interface SettingPresenter {

    void showResetAllQuestionDialog(String message, DialogInterface.OnClickListener okListener);

    void resetAllQuestion();
}
