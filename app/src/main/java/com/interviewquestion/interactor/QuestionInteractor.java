package com.interviewquestion.interactor;

import com.interviewquestion.repository.Question;

import java.util.List;

import retrofit2.Call;

/**
 * Created by root on 28/9/16.
 */

public interface QuestionInteractor {

    void getJavaQuestions(OnQuestionResponseListener questionResponseListener, Call<Question> questionCall);

    void getAndroidQuestions(OnQuestionResponseListener questionResponseListener, Call<Question> questionCall);

    void getIosQuestion(OnQuestionResponseListener questionResponseListener, Call<Question> questionCall);

    interface OnQuestionResponseListener {
        void onSuccess(List<Question.Response> questionList);

        void onError(String error);
    }
}
