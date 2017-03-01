package com.tech.quiz.interactor;

import com.tech.quiz.models.bean.Discussion;
import com.tech.quiz.repositories.interactor.DiscussionInterActor;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Sachin Narang
 */

public class DiscussionInterActorImpl implements DiscussionInterActor {

    @Override
    public Subscription getDiscussion(final OnDiscussionResponseListener questionResponseListener, Observable<Discussion> discussionCall) {
        return discussionCall.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<Discussion>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        questionResponseListener.onError(t.getMessage());
                    }

                    @Override
                    public void onNext(Discussion discussion) {
                        questionResponseListener.onSuccess(discussion.getResponse().get(0));
                    }
                });
    }
}
