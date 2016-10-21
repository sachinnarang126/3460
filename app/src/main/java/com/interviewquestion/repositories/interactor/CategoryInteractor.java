package com.interviewquestion.repositories.interactor;

import com.interviewquestion.models.databasemodel.Questions;

import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryInteractor {

    void getJavaQuestions(OnQuestionResponseListener questionResponseListener, boolean isShowAnsweredQuestion);

    void getAndroidQuestions(OnQuestionResponseListener questionResponseListener, boolean isShowAnsweredQuestion);

    void getIosQuestion(OnQuestionResponseListener questionResponseListener, boolean isShowAnsweredQuestion);

    interface OnQuestionResponseListener {
        <T extends Questions> void onSuccess(List<T> questionListFromDB);

        void onError(String error, boolean hasToLoadQuestionFromDb);
    }
}
