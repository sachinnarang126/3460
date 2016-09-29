package com.interviewquestion.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.interviewquestion.R;
import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.basecontroller.AppCompatFragment;
import com.interviewquestion.interfaces.OnItemClickListener;
import com.interviewquestion.presenter.CategoryPresenter;
import com.interviewquestion.presenter.CategoryPresenterImpl;
import com.interviewquestion.view.CategoryView;

import java.lang.ref.WeakReference;

public class CategoryFragment extends AppCompatFragment implements CategoryView, OnItemClickListener.OnItemClickCallback {

    boolean isServiceExecuted;
    private ProgressBar progressBar;
    private CategoryPresenter categoryPresenter;

    public static CategoryFragment getInstance(String technology, int serviceType) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("serviceType", serviceType);
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
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(categoryPresenter.initCategoryAdapter());

        if (!isServiceExecuted) {
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
            case 1:
                menu.findItem(R.id.action_android).setVisible(false);
                menu.findItem(R.id.action_ios).setVisible(true);
                menu.findItem(R.id.action_java).setVisible(true);
                break;

            case 2:
                menu.findItem(R.id.action_android).setVisible(true);
                menu.findItem(R.id.action_ios).setVisible(false);
                menu.findItem(R.id.action_java).setVisible(true);
                break;

            case 3:
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
                getArguments().putInt("serviceType", 1);
                getArguments().putString("technology", getString(R.string.android));
                getActivity().setTitle(getString(R.string.android));
                getActivity().invalidateOptionsMenu();

                categoryPresenter.prepareToFetchQuestion(1);
                break;

            case R.id.action_ios:
                getArguments().putInt("serviceType", 2);
                getArguments().putString("technology", getString(R.string.ios));
                getActivity().setTitle(getString(R.string.ios));
                getActivity().invalidateOptionsMenu();

                categoryPresenter.prepareToFetchQuestion(2);
                break;

            case R.id.action_java:
                getArguments().putInt("serviceType", 3);
                getArguments().putString("technology", getString(R.string.java));
                getActivity().setTitle(getString(R.string.java));
                getActivity().invalidateOptionsMenu();
                categoryPresenter.prepareToFetchQuestion(3);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryPresenter.onDestroy();
    }

    @Override
    public void onItemClicked(View view, int position) {
        categoryPresenter.showQuestions(position);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

}
