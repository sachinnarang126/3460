package com.tech.quiz.view.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tech.R;
import com.tech.quiz.interfaces.OnItemClickListener;
import com.tech.quiz.presenter.CategoryPresenterImpl;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.views.CategoryView;

import library.basecontroller.AppCompatFragment;

/**
 * @author Sachin Narang
 */

public class CategoryFragment extends AppCompatFragment<CategoryPresenterImpl> implements CategoryView, OnItemClickListener.OnItemClickCallback {

    //    private boolean isDataFetchedFromDB;
    private FrameLayout progressBar;
    private boolean isVisibleToUser;

    public static CategoryFragment getInstance(String technology, int serviceType) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("serviceType", serviceType);
        bundle.putInt("recoveryServiceType", serviceType);
        bundle.putString("technology", technology);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    protected CategoryPresenterImpl onAttachPresenter() {
        return new CategoryPresenterImpl(this, getContext());
    }

    @Override
    protected void initUI(View view) {

        progressBar = (FrameLayout) view.findViewById(R.id.progressBarContainer);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(getPresenter().initCategoryAdapter());

        /*if (!isDataFetchedFromDB) {
            isDataFetchedFromDB = true;
            getPresenter().prepareToFetchQuestionFromDB(getArguments().getInt("serviceType"));
        }*/
    }

    @Override
    public boolean getUserVisibleHint() {
        return isVisibleToUser;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && getPresenter() != null)
            getPresenter().thisTechnologyHasUnAnsweredQuestion();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onItemClicked(View view, int position) {
        getPresenter().showQuestions(position);
    }

    private void showSnackBar(String error) {
        getArguments().putInt("serviceType", Constant.SHOW_ALL);
        getActivity().invalidateOptionsMenu();
        try {
            Snackbar.make(getView().findViewById(R.id.relativeParent), error, Snackbar.LENGTH_LONG).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TextView getQuestionCountView() {
        return (TextView) getView().findViewById(R.id.txtQuestionCount);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        showSnackBar(error);
    }

    @Override
    public void manageRecyclerView(int visibility) {
        try {
            if (getView() != null)
                getView().findViewById(R.id.recyclerView).setVisibility(visibility);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getServiceType() {
        return getArguments().getInt("serviceType");
    }

}
