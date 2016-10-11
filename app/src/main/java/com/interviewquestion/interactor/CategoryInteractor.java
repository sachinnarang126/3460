package com.interviewquestion.interactor;

import com.interviewquestion.repository.Question;

import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryInteractor {

    void getJavaQuestions(OnQuestionResponseListener questionResponseListener);

    void getAndroidQuestions(OnQuestionResponseListener questionResponseListener);

    void getIosQuestion(OnQuestionResponseListener questionResponseListener);

    interface OnQuestionResponseListener {
        void onSuccess(List<Question.Response> questionList);

        void onError(String error);
    }
}
