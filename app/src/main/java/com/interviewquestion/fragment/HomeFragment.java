package com.interviewquestion.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.interviewquestion.R;
import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.basecontroller.AppCompatFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends AppCompatFragment implements View.OnClickListener {

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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
    public void onClick(View view) {
        int serviceType = 0;
        switch (view.getId()) {
            case R.id.txtAndroid:
                serviceType = 1;
                break;

            case R.id.txtJava:
                serviceType = 2;
                break;

            case R.id.txtIos:
                serviceType = 3;
                break;
        }
        ((HomeActivity) getActivity()).startFragmentTransactionAllowingBackStack(CategoryFragment.getInstance(serviceType),
                getString(R.string.category_fragment), R.id.fragment_container);
    }
}