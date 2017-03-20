package com.tech.quiz.interactor;

import com.tech.quiz.models.bean.AskQuestionResponse;
import com.tech.quiz.repositories.interactor.AskQuestionInterActor;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Sachin Narang
 */

public class AskQuestionInterActorImpl implements AskQuestionInterActor {

    @Override
    public Subscription askQuestion(final OnAskQuestionResponseListener onAskQuestionResponseListener, Observable<AskQuestionResponse> askQuestionCall) {
        return askQuestionCall.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<AskQuestionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        onAskQuestionResponseListener.onError(t.getMessage());
                    }

                    @Override
                    public void onNext(AskQuestionResponse questionResponse) {
                        onAskQuestionResponseListener.onSuccess(questionResponse);
                    }
                });
    }
}
