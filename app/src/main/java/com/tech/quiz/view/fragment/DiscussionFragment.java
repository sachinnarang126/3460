package com.tech.quiz.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tech.R;
import com.tech.quiz.presenter.DiscussionPresenterImpl;
import com.tech.quiz.view.views.DiscussionView;

import library.basecontroller.AppCompatFragment;

/**
 * @author Sachin Narang
 */

public class DiscussionFragment extends AppCompatFragment<DiscussionPresenterImpl> implements DiscussionView {

    private FrameLayout progressBar;

    public static DiscussionFragment getInstance() {
        return new DiscussionFragment();
    }

    /**
     * In child fragment you must provide presenter implementation to this,
     * otherwise it will give a null pointer exception
     *
     * @return return the presenterImp instance
     */
    @Override
    protected DiscussionPresenterImpl onAttachPresenter() {
        return new DiscussionPresenterImpl(this, getContext());
    }

    /**
     * initialized the ui component
     *
     * @param view view inflated from xml
     */
    @Override
    protected void initUI(View view) {
        progressBar = (FrameLayout) view.findViewById(R.id.progressBarContainer);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(getPresenter().initAdapter());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_discussion, container, false);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        showSnackBar(error);
    }

    private void showSnackBar(String error) {
        if (getView() != null)
            Snackbar.make(getView().findViewById(R.id.container), error, Snackbar.LENGTH_LONG).show();
    }
}
