package com.tech.quiz.repositories.interactor;

import com.tech.quiz.models.bean.Discussion;

import java.util.List;

import library.mvp.IBaseInterActor;
import rx.Observable;
import rx.Subscription;

/**
 * @author Sachin Narang
 */

public interface DiscussionInterActor extends IBaseInterActor {

    Subscription getDiscussion(DiscussionInterActor.OnDiscussionResponseListener onDiscussionResponseListener, Observable<Discussion> discussionCall);

    interface OnDiscussionResponseListener {
        void onSuccess(List<Discussion> discussionList);

        void onError(String error);
    }
}
