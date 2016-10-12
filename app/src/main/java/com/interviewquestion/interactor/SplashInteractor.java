package com.interviewquestion.interactor;

import com.interviewquestion.repository.Question;

import java.util.List;

import retrofit2.Call;

/**
 * Created by root on 28/9/16.
 */

public interface SplashInteractor {

    void getJavaQuestions(OnJavaQuestionResponseListener questionResponseListener, Call<Question> questionCall);

    void getAndroidQuestions(OnAndroidQuestionResponseListener questionResponseListener, Call<Question> questionCall);

    void getIosQuestion(OnIosQuestionResponseListener questionResponseListener, Call<Question> questionCall);

    interface OnJavaQuestionResponseListener {
        void onSuccess(List<Question.Response> questionList, int serviceType);

        void onError(String error);
    }

    interface OnAndroidQuestionResponseListener {
        void onSuccess(List<Question.Response> questionList, int serviceType);

        void onError(String error);
    }

    interface OnIosQuestionResponseListener {
        void onSuccess(List<Question.Response> questionList, int serviceType);

        void onError(String error);
    }
}
