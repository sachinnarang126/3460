package com.interviewquestion.repositories.interactor;

import com.interviewquestion.models.QuestionResponse;

import java.util.List;

import retrofit2.Call;

/**
 * Created by root on 28/9/16.
 */

public interface HomeInteractor {

    void getJavaQuestions(OnJavaQuestionResponseListener questionResponseListener, Call<QuestionResponse> questionCall);

    void getAndroidQuestions(OnAndroidQuestionResponseListener questionResponseListener, Call<QuestionResponse> questionCall);

    void getIosQuestion(OnIosQuestionResponseListener questionResponseListener, Call<QuestionResponse> questionCall);

    interface OnJavaQuestionResponseListener {
        void onSuccess(List<QuestionResponse.Response> questionList, int serviceType);
        void onError(String error);
    }

    interface OnAndroidQuestionResponseListener {
        void onSuccess(List<QuestionResponse.Response> questionList, int serviceType);
        void onError(String error);
    }

    interface OnIosQuestionResponseListener {
        void onSuccess(List<QuestionResponse.Response> questionList, int serviceType);
        void onError(String error);
    }
}
