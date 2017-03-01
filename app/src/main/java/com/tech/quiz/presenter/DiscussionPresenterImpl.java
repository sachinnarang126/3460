package com.tech.quiz.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.tech.R;
import com.tech.quiz.adapter.DiscussionAdapter;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.interactor.DiscussionInterActorImpl;
import com.tech.quiz.interfaces.OnItemClickListener;
import com.tech.quiz.models.bean.Discussion;
import com.tech.quiz.network.RetrofitApiService;
import com.tech.quiz.network.RetrofitClient;
import com.tech.quiz.repositories.interactor.DiscussionInterActor;
import com.tech.quiz.repositories.presenter.DiscussionPresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.fragment.DiscussionDetailFragment;
import com.tech.quiz.view.views.DiscussionView;

import java.util.ArrayList;
import java.util.List;

import library.mvp.FragmentPresenter;
import rx.Observable;

/**
 * @author Sachin Narang
 */

public class DiscussionPresenterImpl extends FragmentPresenter<DiscussionView, DiscussionInterActorImpl> implements
        DiscussionPresenter, DiscussionInterActor.OnDiscussionResponseListener, OnItemClickListener.OnItemClickCallback {

    private List<Discussion.Response> mDiscussionList;
    private DiscussionAdapter discussionAdapter;

    public DiscussionPresenterImpl(DiscussionView discussionView, Context context) {
        super(discussionView, context);
        mDiscussionList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mDiscussionList.size() == 0) {
            getView().showProgressBar();
            RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
            Observable<Discussion> discussionObservable = apiService.getDiscussion();
            putSubscriberInMap(getInterActor().getDiscussion(this, discussionObservable), Constant.DISCUSSION_URL);
        }
    }

    @Override
    protected DiscussionInterActorImpl createInterActor() {
        return new DiscussionInterActorImpl();
    }

    @Override
    public DiscussionAdapter initAdapter() {
        return discussionAdapter = new DiscussionAdapter(mDiscussionList, getActivity().isSubscribedUser(),
                getContext(), this);
    }

    @Override
    public void onSuccess(List<Discussion.Response> discussionList) {
        mDiscussionList.clear();
        mDiscussionList.addAll(discussionList);
        if (discussionAdapter != null)
            discussionAdapter.notifyDataSetChanged();
        getView().hideProgressBar();
    }

    @Override
    public void onError(String error) {
        getView().hideProgressBar();
        getView().onError(error);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Discussion.Response response = mDiscussionList.get(position);
        DataHolder.getInstance().setDiscussion(response);
        if (response.getStatus().equalsIgnoreCase("1")) {
            if (getActivity().isSubscribedUser()) {
                startFragmentTransaction(DiscussionDetailFragment.getInstance(), "DiscussionDetail", R.id.container);
            } else {
                //show that you are not a subscribed user
            }
        } else {
            startFragmentTransaction(DiscussionDetailFragment.getInstance(), "DiscussionDetail", R.id.container);
        }
    }

    private void startFragmentTransaction(Fragment fragment, String tag, int container) {
        try {
            FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
            Fragment fragmentFromBackStack = mFragmentManager.findFragmentByTag(tag);
            if (fragmentFromBackStack == null) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(container, fragment, tag);
                fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commit();
            } else {
                // this called if add to back stack
                mFragmentManager.popBackStack(tag, 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
