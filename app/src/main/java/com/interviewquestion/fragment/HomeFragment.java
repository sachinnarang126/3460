package com.interviewquestion.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.interviewquestion.R;
import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.activity.SettingsActivity;
import com.interviewquestion.basecontroller.AppCompatFragment;
import com.interviewquestion.presenter.HomePresenterImpl;
import com.interviewquestion.repositories.presenter.HomePresenter;
import com.interviewquestion.util.Constant;
import com.interviewquestion.view.HomeView;

import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends AppCompatFragment implements View.OnClickListener, HomeView {

    private HomePresenter homePresenter;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getActivity().setTitle("Test your Skills");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeakReference<HomeView> weakReference = new WeakReference<HomeView>(this);
        homePresenter = new HomePresenterImpl(weakReference);
        homePresenter.prepareToFetchQuestion();
    }

    @Override
    public void onDestroy() {
        homePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtAndroid = (TextView) view.findViewById(R.id.txtAndroid);
        TextView txtJava = (TextView) view.findViewById(R.id.txtJava);
        TextView txtIos = (TextView) view.findViewById(R.id.txtIos);
        txtAndroid.setOnClickListener(this);
        txtJava.setOnClickListener(this);
        txtIos.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int serviceType = 0;
        String technology = "";
        switch (view.getId()) {
            case R.id.txtAndroid:
                serviceType = Constant.ANDROID;
                technology = getString(R.string.android);
                break;

            case R.id.txtIos:
                serviceType = Constant.IOS;
                technology = getString(R.string.ios);
                break;

            case R.id.txtJava:
                serviceType = Constant.JAVA;
                technology = getString(R.string.java);
                break;

        }
        ((HomeActivity) getActivity()).startFragmentTransactionAllowingBackStack(CategoryFragment.getInstance(technology, serviceType),
                getString(R.string.category_fragment), R.id.fragment_container);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess() {

    }
}
