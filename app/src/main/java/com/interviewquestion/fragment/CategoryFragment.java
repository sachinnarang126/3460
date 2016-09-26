package com.interviewquestion.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.interviewquestion.R;
import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.adapter.CategoryAdapter;
import com.interviewquestion.basecontroller.AppCompatFragment;
import com.interviewquestion.network.RetrofitApiService;
import com.interviewquestion.network.RetrofitClient;
import com.interviewquestion.repository.Question;
import com.interviewquestion.util.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends AppCompatFragment {

    private CategoryAdapter categoryAdapter;
    private List<String> categoryList;
    private ProgressBar progressBar;
    boolean isServiceExecuted;
    private RecyclerView recyclerView;

    public static CategoryFragment getInstance(/*String url, */int serviceType) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("serviceType", serviceType);
//        bundle.putString("url", url);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryList = new ArrayList<>();
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

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(categoryAdapter);

        if (!isServiceExecuted) {
            switch (getArguments().getInt("serviceType")) {
                case 1:
                    getAndroidQuestion();
                    break;

                case 2:
                    getIosQuestion();
                    break;

                case 3:
                    getJavaQuestion();
                    break;
            }
        } /*else {

        }*/
    }

    private void getAndroidQuestion() {
        if (((HomeActivity) getActivity()).isInternetAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<Question> questionCall = retrofitApiService.getAndroidQuestion();
            putServiceCallInServiceMap(questionCall, Constant.ANDROID);

            questionCall.enqueue(new Callback<Question>() {
                @Override
                public void onResponse(Call<Question> call, Response<Question> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        isServiceExecuted = true;
                        updateUI(response.body().getResponse().get(0));
                    }
                }

                @Override
                public void onFailure(Call<Question> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void getIosQuestion() {
        if (((HomeActivity) getActivity()).isInternetAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<Question> questionCall = retrofitApiService.getIosQuestion();
            putServiceCallInServiceMap(questionCall, Constant.IOS);

            questionCall.enqueue(new Callback<Question>() {
                @Override
                public void onResponse(Call<Question> call, Response<Question> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        isServiceExecuted = true;
                        updateUI(response.body().getResponse().get(0));
                    }
                }

                @Override
                public void onFailure(Call<Question> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void getJavaQuestion() {
        if (((HomeActivity) getActivity()).isInternetAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            RetrofitApiService retrofitApiService = RetrofitClient.getRetrofitClient();
            Call<Question> questionCall = retrofitApiService.getJavaQuestion();
            putServiceCallInServiceMap(questionCall, Constant.JAVA);

            questionCall.enqueue(new Callback<Question>() {
                @Override
                public void onResponse(Call<Question> call, Response<Question> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        isServiceExecuted = true;
                        updateUI(response.body().getResponse().get(0));
                    }
                }

                @Override
                public void onFailure(Call<Question> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void updateUI(List<Question.Response> responseList) {
        categoryList.clear();
        categoryList.add("All");
        for (Question.Response response : responseList) {
            System.out.println("response.getCategory() " + response.getCategory());
            categoryList.add(response.getCategory());
        }

        System.out.println("categoryList.size " + categoryList.size());
        categoryAdapter.notifyDataSetChanged();
        /*categoryAdapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(categoryAdapter);*/
    }
}