package com.tech.quiz.repositories.interactor;

import com.tech.quiz.models.bean.QuestionResponse;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by root on 28/9/16.
 */

public interface SplashInteractor {

    Subscription getJavaQuestions(OnJavaQuestionResponseListener questionResponseListener, Observable<QuestionResponse> questionCall);

    Subscription getAndroidQuestions(OnAndroidQuestionResponseListener questionResponseListener, Observable<QuestionResponse> questionCall);

    Subscription getIosQuestion(OnIosQuestionResponseListener questionResponseListener, Observable<QuestionResponse> questionCall);

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
