package com.tech.quiz.interactor;

import com.tech.quiz.models.bean.QuestionResponse;
import com.tech.quiz.repositories.interactor.HomeInteractor;
import com.tech.quiz.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 28/9/16.
 */

public class HomeInteractorImpl implements HomeInteractor {

    @Override
    public void getJavaQuestions(final OnJavaQuestionResponseListener questionResponseListener, Call<QuestionResponse> questionCall) {
        questionCall.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful()) {
                    questionResponseListener.onSuccess(response.body().getResponse().get(0), Constant.JAVA);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                t.printStackTrace();
                questionResponseListener.onError(t.getMessage());
            }
        });
    }


    @Override
    public void getAndroidQuestions(final OnAndroidQuestionResponseListener questionResponseListener, Call<QuestionResponse> questionCall) {

        questionCall.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful()) {
                    questionResponseListener.onSuccess(response.body().getResponse().get(0), Constant.ANDROID);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                t.printStackTrace();
                questionResponseListener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getIosQuestion(final OnIosQuestionResponseListener questionResponseListener, Call<QuestionResponse> questionCall) {

        questionCall.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful()) {
                    questionResponseListener.onSuccess(response.body().getResponse().get(0), Constant.IOS);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                t.printStackTrace();
                questionResponseListener.onError(t.getMessage());
            }
        });
    }
}
