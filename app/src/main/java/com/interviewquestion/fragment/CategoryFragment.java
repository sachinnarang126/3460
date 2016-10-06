package com.interviewquestion.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.interviewquestion.R;
import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.basecontroller.AppCompatFragment;
import com.interviewquestion.interfaces.OnItemClickListener;
import com.interviewquestion.presenter.CategoryPresenter;
import com.interviewquestion.presenter.CategoryPresenterImpl;
import com.interviewquestion.util.Constant;
import com.interviewquestion.view.CategoryView;

import java.lang.ref.WeakReference;

public class CategoryFragment extends AppCompatFragment implements CategoryView, OnItemClickListener.OnItemClickCallback {

    private boolean isServiceExecuted, isServiceExecuting;
    private FrameLayout progressBar;
    private CategoryPresenter categoryPresenter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        WeakReference<CategoryView> reference = new WeakReference<CategoryView>(this);
        categoryPresenter = new CategoryPresenterImpl(reference);

        getActivity().setTitle(getArguments().getString("technology"));
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (FrameLayout) view.findViewById(R.id.progressBarContainer);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(categoryPresenter.initCategoryAdapter());

        if (!isServiceExecuted) {
            isServiceExecuted = true;
            categoryPresenter.prepareToFetchQuestion(getArguments().getInt("serviceType"));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_category, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        switch (getArguments().getInt("serviceType")) {
            case Constant.SHOW_ALL:
                menu.findItem(R.id.action_android).setVisible(true);
                menu.findItem(R.id.action_ios).setVisible(true);
                menu.findItem(R.id.action_java).setVisible(true);
                break;

            case Constant.ANDROID:
                menu.findItem(R.id.action_android).setVisible(false);
                menu.findItem(R.id.action_ios).setVisible(true);
                menu.findItem(R.id.action_java).setVisible(true);
                break;

            case Constant.IOS:
                menu.findItem(R.id.action_android).setVisible(true);
                menu.findItem(R.id.action_ios).setVisible(false);
                menu.findItem(R.id.action_java).setVisible(true);
                break;

            case Constant.JAVA:
                menu.findItem(R.id.action_android).setVisible(true);
                menu.findItem(R.id.action_ios).setVisible(true);
                menu.findItem(R.id.action_java).setVisible(false);
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;

            case R.id.action_android:
                getArguments().putInt("serviceType", Constant.ANDROID);
                getArguments().putString("technology", getString(R.string.android));
                getActivity().setTitle(getString(R.string.android));
                getActivity().invalidateOptionsMenu();

                categoryPresenter.prepareToFetchQuestion(Constant.ANDROID);
                break;

            case R.id.action_ios:
                getArguments().putInt("serviceType", Constant.IOS);
                getArguments().putString("technology", getString(R.string.ios));
                getActivity().setTitle(getString(R.string.ios));
                getActivity().invalidateOptionsMenu();

                categoryPresenter.prepareToFetchQuestion(Constant.IOS);
                break;

            case R.id.action_java:
                getArguments().putInt("serviceType", Constant.JAVA);
                getArguments().putString("technology", getString(R.string.java));
                getActivity().setTitle(getString(R.string.java));
                getActivity().invalidateOptionsMenu();

                categoryPresenter.prepareToFetchQuestion(Constant.JAVA);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        categoryPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onItemClicked(View view, int position) {
        if (!isServiceExecuting)
            categoryPresenter.showQuestions(position);
    }

    private void showToast(String error) {
        getArguments().putInt("serviceType", Constant.SHOW_ALL);
        getActivity().invalidateOptionsMenu();
        Snackbar.make(getView().findViewById(R.id.relativeParent), error, Snackbar.LENGTH_LONG).show();

        /*Snackbar snack = Snackbar.make(overViewRelLay, R.string.snackbar_text, Snackbar.LENGTH_LONG).
                setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        switch (event) {

                            case Snackbar.Callback.DISMISS_EVENT_ACTION:

                                break;

                            case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:

                                break;
                        }
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {

                    }
                }).setAction(R.string.snackbar_action_undo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snack.show();*/
    }

    @Override
    public void showProgress() {
        isServiceExecuting = true;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        isServiceExecuting = false;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        showToast(error);
    }

}
