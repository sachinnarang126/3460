package com.tech.quiz.view.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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

    private boolean isDataFetchedFromDB;
    private FrameLayout progressBar;

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
        /*AdView mAdView = (AdView) view.findViewById(R.id.adView);
        if (!((HomeActivity) getActivity()).isSubscribedUser()) {
            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("9210683FFFBDE1953CE613AB2FDE46E5").
                            addTestDevice("F56162DD974939BBF71A8D3E8CC8A44A").
                            addTestDevice("1FBF7D7CF19C0C11158AF44FDA595121").
                            addTestDevice("F58DA099F52C8D53E4DD635D0C5EB709").build();

            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }*/

        progressBar = (FrameLayout) view.findViewById(R.id.progressBarContainer);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(getPresenter().initCategoryAdapter());

        if (!isDataFetchedFromDB) {
            isDataFetchedFromDB = true;
            getPresenter().prepareToFetchQuestionFromDB(getArguments().getInt("serviceType"));
        }
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!((HomeActivity) getActivity()).isSubscribedUser()) {
            MobileAds.initialize(getActivity().getApplicationContext(), getString(R.string.category_footer));
        }

        setHasOptionsMenu(true);
        getActivity().setTitle(getArguments().getString("technology"));
        try {
            ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_category, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/

    /*@Override
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
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;

            case R.id.action_android:
                getPresenter().clearCategoryAdapter();
                getArguments().putInt("serviceType", Constant.ANDROID);
                getArguments().putString("technology", getString(R.string.android));
                getActivity().setTitle(getString(R.string.android));
                getActivity().invalidateOptionsMenu();

                getPresenter().prepareToFetchQuestionFromDB(Constant.ANDROID);
                break;

            case R.id.action_ios:
                getPresenter().clearCategoryAdapter();
                getArguments().putInt("serviceType", Constant.IOS);
                getArguments().putString("technology", getString(R.string.ios));
                getActivity().setTitle(getString(R.string.ios));
                getActivity().invalidateOptionsMenu();

                getPresenter().prepareToFetchQuestionFromDB(Constant.IOS);
                break;

            case R.id.action_java:
                getPresenter().clearCategoryAdapter();
                getArguments().putInt("serviceType", Constant.JAVA);
                getArguments().putString("technology", getString(R.string.java));
                getActivity().setTitle(getString(R.string.java));
                getActivity().invalidateOptionsMenu();

                getPresenter().prepareToFetchQuestionFromDB(Constant.JAVA);
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/

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
            getView().findViewById(R.id.recyclerView).setVisibility(visibility);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
