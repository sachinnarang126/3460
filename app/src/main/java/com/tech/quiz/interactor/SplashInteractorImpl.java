package com.tech.quiz.interactor;

import com.tech.quiz.models.bean.QuestionResponse;
import com.tech.quiz.repositories.interactor.SplashInteractor;
import com.tech.quiz.util.Constant;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 28/9/16.
 */

public class SplashInteractorImpl implements SplashInteractor {

    @Override
    public Subscription getJavaQuestions(final OnJavaQuestionResponseListener questionResponseListener, Observable<QuestionResponse> questionCall) {
        /*questionCall.enqueue(new Callback<QuestionResponse>() {
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
        });*/

        return questionCall.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<QuestionResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                questionResponseListener.onError(t.getMessage());
            }

            @Override
            public void onNext(QuestionResponse questionResponse) {
                questionResponseListener.onSuccess(questionResponse.getResponse().get(0), Constant.JAVA);
            }
        });
    }

    @Override
    public Subscription getAndroidQuestions(final OnAndroidQuestionResponseListener questionResponseListener, Observable<QuestionResponse> questionCall) {

        /*questionCall.enqueue(new Callback<QuestionResponse>() {
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
        });*/

        return questionCall.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<QuestionResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                questionResponseListener.onError(t.getMessage());
            }

            @Override
            public void onNext(QuestionResponse questionResponse) {
                questionResponseListener.onSuccess(questionResponse.getResponse().get(0), Constant.ANDROID);
            }
        });
    }

    @Override
    public Subscription getIosQuestion(final OnIosQuestionResponseListener questionResponseListener, Observable<QuestionResponse> questionCall) {

        /*questionCall.enqueue(new Callback<QuestionResponse>() {
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
        });*/

        return questionCall.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<QuestionResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                questionResponseListener.onError(t.getMessage());
            }

            @Override
            public void onNext(QuestionResponse questionResponse) {
                questionResponseListener.onSuccess(questionResponse.getResponse().get(0), Constant.IOS);
            }
        });
    }
}
