package com.tech.quiz.repositories.interactor;

import com.tech.quiz.models.bean.AskQuestionResponse;

import library.mvp.IBaseInterActor;
import rx.Observable;
import rx.Subscription;

/**
 * @author Sachin Narang
 */

public interface AskQuestionInterActor extends IBaseInterActor {
    Subscription askQuestion(OnAskQuestionResponseListener onAskQuestionResponseListener, Observable<AskQuestionResponse> askQuestionCall);

    interface OnAskQuestionResponseListener {
        void onSuccess(AskQuestionResponse askQuestionResponse);

        void onError(String error);
    }
}
