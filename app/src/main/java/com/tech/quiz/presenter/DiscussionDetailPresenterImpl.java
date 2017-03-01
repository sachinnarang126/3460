package com.tech.quiz.presenter;

import android.content.Context;

import com.tech.quiz.repositories.presenter.DiscussionDetailPresenter;
import com.tech.quiz.view.views.DiscussionDetailView;

import library.mvp.ApplicationInterActor;
import library.mvp.FragmentPresenter;

/**
 * @author Sachin Narang
 */

public class DiscussionDetailPresenterImpl extends FragmentPresenter<DiscussionDetailView, ApplicationInterActor> implements DiscussionDetailPresenter {

    public DiscussionDetailPresenterImpl(DiscussionDetailView discussionDetailView, Context context) {
        super(discussionDetailView, context);
    }

    @Override
    protected ApplicationInterActor createInterActor() {
        return null;
    }
}
