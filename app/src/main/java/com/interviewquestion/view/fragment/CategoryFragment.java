package com.interviewquestion.view.fragment;


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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.interviewquestion.R;
import com.interviewquestion.basecontroller.AppCompatFragment;
import com.interviewquestion.interfaces.OnItemClickListener;
import com.interviewquestion.presenter.CategoryPresenterImpl;
import com.interviewquestion.repositories.presenter.CategoryPresenter;
import com.interviewquestion.util.Constant;
import com.interviewquestion.view.activity.HomeActivity;
import com.interviewquestion.view.views.CategoryView;

import java.lang.ref.WeakReference;

public class CategoryFragment extends AppCompatFragment implements CategoryView, OnItemClickListener.OnItemClickCallback {

    private boolean isDataFetchedFromDB;
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
        MobileAds.initialize(getActivity().getApplicationContext(), getString(R.string.category_footer));
        setHasOptionsMenu(true);

        WeakReference<CategoryView> reference = new WeakReference<CategoryView>(this);
        categoryPresenter = new CategoryPresenterImpl(reference);

        getActivity().setTitle(getArguments().getString("technology"));
        try {
            ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()/*.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("9210683FFFBDE1953CE613AB2FDE46E5").
                        addTestDevice("F56162DD974939BBF71A8D3E8CC8A44A")*/.build();

        mAdView.loadAd(adRequest);

        progressBar = (FrameLayout) view.findViewById(R.id.progressBarContainer);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(categoryPresenter.initCategoryAdapter());

        if (!isDataFetchedFromDB) {
            isDataFetchedFromDB = true;
            categoryPresenter.prepareToFetchQuestionFromDB(getArguments().getInt("serviceType"));
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
                categoryPresenter.clearCategoryAdapter();
                getArguments().putInt("serviceType", Constant.ANDROID);
                getArguments().putString("technology", getString(R.string.android));
                getActivity().setTitle(getString(R.string.android));
                getActivity().invalidateOptionsMenu();

                categoryPresenter.prepareToFetchQuestionFromDB(Constant.ANDROID);
                break;

            case R.id.action_ios:
                categoryPresenter.clearCategoryAdapter();
                getArguments().putInt("serviceType", Constant.IOS);
                getArguments().putString("technology", getString(R.string.ios));
                getActivity().setTitle(getString(R.string.ios));
                getActivity().invalidateOptionsMenu();

                categoryPresenter.prepareToFetchQuestionFromDB(Constant.IOS);
                break;

            case R.id.action_java:
                categoryPresenter.clearCategoryAdapter();
                getArguments().putInt("serviceType", Constant.JAVA);
                getArguments().putString("technology", getString(R.string.java));
                getActivity().setTitle(getString(R.string.java));
                getActivity().invalidateOptionsMenu();

                categoryPresenter.prepareToFetchQuestionFromDB(Constant.JAVA);
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
        categoryPresenter.showQuestions(position);
    }

    private void showSnackBar(String error) {
        getArguments().putInt("serviceType", Constant.SHOW_ALL);
        getActivity().invalidateOptionsMenu();
        Snackbar.make(getView().findViewById(R.id.relativeParent), error, Snackbar.LENGTH_LONG).show();
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

}
