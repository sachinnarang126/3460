package com.interviewquestion.fragment;


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
import com.interviewquestion.basecontroller.AppCompatFragment;
import com.interviewquestion.util.Constant;

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
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getActivity().setTitle("Test your Skills");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        /*List<Integer> idList = new ArrayList<>();
        idList.add(1);
        idList.add(4);
        idList.add(6);

        Call<Question> testCall = RetrofitClient.getRetrofitClient().iosSelectedQuestion(idList);

        testCall.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                System.out.println("HomeFragment.onResponse");
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                System.out.println("HomeFragment.onFailure");
                t.printStackTrace();
            }
        });*/
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
}
