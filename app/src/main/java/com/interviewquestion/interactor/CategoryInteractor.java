package com.interviewquestion.interactor;

import com.interviewquestion.repository.databasemodel.Questions;

import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryInteractor {

    void getJavaQuestions(OnQuestionResponseListener questionResponseListener);

    void getAndroidQuestions(OnQuestionResponseListener questionResponseListener);

    void getIosQuestion(OnQuestionResponseListener questionResponseListener);

    interface OnQuestionResponseListener {
        <T extends Questions> void onSuccess(List<T> questionListFromDB);

        void onError(String error);
    }
}
