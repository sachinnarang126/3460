package com.tech.quiz.interactor;

import com.tech.quiz.models.bean.Discussion;
import com.tech.quiz.models.bean.QuestionResponse;
import com.tech.quiz.repositories.interactor.DiscussionInterActor;

import rx.Observable;
import rx.Subscription;

/**
 * @author Sachin Narang
 */

public class DiscussionInterActorImpl implements DiscussionInterActor {

    @Override
    public Subscription getDiscussion(OnDiscussionResponseListener questionResponseListener, Observable<Discussion> discussionCall) {
        return null;
    }
}
