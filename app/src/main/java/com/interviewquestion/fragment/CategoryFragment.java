package com.interviewquestion.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.interviewquestion.R;
import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.activity.QuestionActivity;
import com.interviewquestion.adapter.CategoryAdapter;
import com.interviewquestion.basecontroller.AppCompatFragment;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.interfaces.OnItemClickListener;
import com.interviewquestion.network.RetrofitApiService;
import com.interviewquestion.network.RetrofitClient;
import com.interviewquestion.repository.Question;
import com.interviewquestion.util.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends AppCompatFragment implements OnItemClickListener.OnItemClickCallback {

    boolean isServiceExecuted;
    private CategoryAdapter categoryAdapter;
    private List<String> categoryList;
    private ProgressBar progressBar;
    private List<Question.Response> questionList;

    public static CategoryFragment getInstance(String technology, int serviceType) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("serviceType", serviceType);
        bundle.putString("technology", technology);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        categoryList = new ArrayList<>();
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
        categoryAdapter = new CategoryAdapter(categoryList, this);
        recyclerView.setAdapter(categoryAdapter);

        if (!isServiceExecuted) {
            switch (getArguments().getInt("serviceType")) {
                case 1:
//                    recyclerView.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.android));
                    getAndroidQuestion();
                    break;

                case 2:
//                    recyclerView.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.ios));
                    getIosQuestion();
                    break;

                case 3:
//                    recyclerView.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.java));
                    getJavaQuestion();
                    break;
            }
        }
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

    public void goToQuestionActivity(int position, String category) {
        if (position == 0) {
            DataHolder.getInstance().setQuestionList(questionList);
        } else {
            List<Question.Response> tempList = new ArrayList<>();
            for (Question.Response response : questionList) {
                if (response.getCategory().equalsIgnoreCase(category)) {
                    tempList.add(response);
                }
            }
            DataHolder.getInstance().setQuestionList(tempList);
        }

        Intent intent = new Intent(getActivity(), QuestionActivity.class);
        intent.putExtra("title", category);
        startActivity(intent);
    }

    private void updateUI(List<Question.Response> responseList) {
        questionList = responseList;
        categoryList.clear();
        categoryList.add("All Question");
        for (Question.Response response : responseList) {
            if (!categoryList.contains(response.getCategory()))
                categoryList.add(response.getCategory());
        }

        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(View view, int position) {
        goToQuestionActivity(position, categoryList.get(position));
    }
}
