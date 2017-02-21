package com.tech.quiz.repositories.interactor;

import com.tech.quiz.models.databasemodel.Questions;

import java.util.List;

import library.mvp.IBaseInterActor;

/**
 * @author Sachin Narang
 */

public interface CategoryInterActor extends IBaseInterActor {

    void getJavaQuestions(OnQuestionResponseListener questionResponseListener, boolean isShowAnsweredQuestion);

    void getAndroidQuestions(OnQuestionResponseListener questionResponseListener, boolean isShowAnsweredQuestion);

    void getIosQuestion(OnQuestionResponseListener questionResponseListener, boolean isShowAnsweredQuestion);

    interface OnQuestionResponseListener {
        <T extends Questions> void onSuccess(List<T> questionListFromDB);

        void onError(String error, boolean hasToLoadQuestionFromDb);
    }
}
