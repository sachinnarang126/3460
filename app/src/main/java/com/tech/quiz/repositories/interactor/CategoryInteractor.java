package com.tech.quiz.repositories.interactor;

import com.tech.quiz.models.databasemodel.Questions;

import java.util.List;

/**
 * @author Sachin Narang
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
