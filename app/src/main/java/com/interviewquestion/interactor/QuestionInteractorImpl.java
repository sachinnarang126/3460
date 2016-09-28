package com.interviewquestion.interactor;

import com.interviewquestion.repository.Question;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 28/9/16.
 */

public class QuestionInteractorImpl implements QuestionInteractor {

    @Override
    public void getJavaQuestions(final OnQuestionResponseListener questionResponseListener, Call<Question> questionCall) {
        questionCall.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful()) {
                    questionResponseListener.onSuccess(response.body().getResponse().get(0));
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                t.printStackTrace();
                questionResponseListener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getAndroidQuestions(final OnQuestionResponseListener questionResponseListener, Call<Question> questionCall) {

        questionCall.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful()) {
                    questionResponseListener.onSuccess(response.body().getResponse().get(0));
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                t.printStackTrace();
                questionResponseListener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getIosQuestion(final OnQuestionResponseListener questionResponseListener, Call<Question> questionCall) {

        questionCall.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful()) {
                    questionResponseListener.onSuccess(response.body().getResponse().get(0));
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                t.printStackTrace();
                questionResponseListener.onError(t.getMessage());
            }
        });
    }
}
