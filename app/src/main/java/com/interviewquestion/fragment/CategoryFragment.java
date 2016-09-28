package com.interviewquestion.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
                    getAndroidQuestion();
                    break;

                case 2:
                    getIosQuestion();
                    break;

                case 3:
                    getJavaQuestion();
                    break;
            }
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
                getArguments().putString("technology", "ANDROID");
                getActivity().setTitle(getString(R.string.android));
                getActivity().invalidateOptionsMenu();
                getAndroidQuestion();
                break;

            case R.id.action_ios:
                getArguments().putInt("serviceType", 2);
                getArguments().putString("technology", "IOS");
                getActivity().setTitle(getString(R.string.ios));
                getActivity().invalidateOptionsMenu();
                getIosQuestion();
                break;

            case R.id.action_java:
                getArguments().putInt("serviceType", 3);
                getArguments().putString("technology", "JAVA");
                getActivity().setTitle(getString(R.string.java));
                getActivity().invalidateOptionsMenu();
                getJavaQuestion();
                break;
        }

        return super.onOptionsItemSelected(item);
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
                    displayDataReloadAlert();
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
                    displayDataReloadAlert();
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
                    displayDataReloadAlert();
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
//                System.out.println("id " + response.getId() + " isAttempted " + response.isAttempted() + " isCorrectAnswerProvided " + response.isCorrectAnswerProvided() + " getUserAnswer " + response.getUserAnswer());
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

    private void displayDataReloadAlert() {
        try {
            if (isAdded() && getActivity() != null) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Question")
                        .setMessage("Error receiving post from server, Reload Again...?")
                        .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
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

                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
