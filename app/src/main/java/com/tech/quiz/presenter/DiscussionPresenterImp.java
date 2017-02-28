package com.tech.quiz.presenter;

import android.content.Context;
import android.os.Bundle;

import com.tech.quiz.adapter.DiscussionAdapter;
import com.tech.quiz.interactor.DiscussionInterActorImpl;
import com.tech.quiz.models.bean.Discussion;
import com.tech.quiz.network.RetrofitApiService;
import com.tech.quiz.network.RetrofitClient;
import com.tech.quiz.repositories.interactor.DiscussionInterActor;
import com.tech.quiz.repositories.presenter.DiscussionPresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.views.DiscussionView;

import java.util.ArrayList;
import java.util.List;

import library.mvp.ActivityPresenter;
import rx.Observable;

/**
 * @author Sachin Narang
 */

public class DiscussionPresenterImp extends ActivityPresenter<DiscussionView, DiscussionInterActorImpl> implements DiscussionPresenter, DiscussionInterActor.OnDiscussionResponseListener {

    private List<Discussion> mDiscussionList;

    public DiscussionPresenterImp(DiscussionView discussionView, Context context) {
        super(discussionView, context);
        mDiscussionList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getView().showProgressBar();
        RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
        Observable<Discussion> discussionObservable = apiService.getDiscussion();
        putSubscriberInMap(getInterActor().getDiscussion(this, discussionObservable), Constant.DISCUSSION_URL);
    }

    @Override
    protected DiscussionInterActorImpl createInterActor() {
        return new DiscussionInterActorImpl();
    }

    @Override
    public DiscussionAdapter initAdapter() {
        return new DiscussionAdapter(mDiscussionList, null);
    }

    @Override
    public void onSuccess(List<Discussion> discussionList) {
        mDiscussionList.clear();
        mDiscussionList.addAll(discussionList);
        getView().hideProgressBar();
    }

    @Override
    public void onError(String error) {
        getView().hideProgressBar();
        getView().onError(error);
    }
}
