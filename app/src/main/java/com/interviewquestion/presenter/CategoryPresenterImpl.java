package com.interviewquestion.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.activity.QuestionActivity;
import com.interviewquestion.adapter.CategoryAdapter;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.fragment.CategoryFragment;
import com.interviewquestion.interactor.CategoryInteractor;
import com.interviewquestion.interactor.CategoryInteractorImpl;
import com.interviewquestion.network.RetrofitApiService;
import com.interviewquestion.network.RetrofitClient;
import com.interviewquestion.repository.Question;
import com.interviewquestion.util.Constant;
import com.interviewquestion.view.CategoryView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by root on 28/9/16.
 */

public class CategoryPresenterImpl implements CategoryPresenter, CategoryInteractor.OnQuestionResponseListener {

    private WeakReference<CategoryView> categoryView;
    private CategoryInteractor categoryInteractor;
    private CategoryAdapter categoryAdapter;
    private List<Question.Response> questionList;

    public CategoryPresenterImpl(WeakReference<CategoryView> categoryView) {
        this.categoryView = categoryView;
        categoryInteractor = new CategoryInteractorImpl();
    }

    @Override
    public void onDestroy() {
        categoryList.clear();
        questionList = null;
        categoryView.clear();
        categoryAdapter = null;
    }

    @Override
    public void prepareToFetchQuestion(int serviceType) {
        if (categoryView.get() != null) {
            CategoryFragment context = ((CategoryFragment) categoryView.get());
            if (((HomeActivity) context.getActivity()).isInternetAvailable()) {

                categoryView.get().showProgress();
                RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
                Call<Question> questionCall;
                switch (serviceType) {
                    case 1:
                        if (context.isServiceCallExist(Constant.ANDROID)) {
                            questionCall = context.getServiceCallIfExist(Constant.ANDROID);
                        } else {
                            questionCall = apiService.getAndroidQuestion();
                            context.putServiceCallInServiceMap(questionCall, Constant.ANDROID);
                        }
                        categoryInteractor.getAndroidQuestions(this, questionCall);
                        break;

                    case 2:
                        if (context.isServiceCallExist(Constant.IOS)) {
                            questionCall = context.getServiceCallIfExist(Constant.IOS);
                        } else {
                            questionCall = apiService.getIosQuestion();
                            context.putServiceCallInServiceMap(questionCall, Constant.IOS);
                        }
                        categoryInteractor.getIosQuestion(this, questionCall);
                        break;

                    case 3:
                        if (context.isServiceCallExist(Constant.JAVA)) {
                            questionCall = context.getServiceCallIfExist(Constant.JAVA);
                        } else {
                            questionCall = apiService.getJavaQuestion();
                            context.putServiceCallInServiceMap(questionCall, Constant.JAVA);
                        }
                        categoryInteractor.getJavaQuestions(this, questionCall);
                        break;
                }
            }

        }
    }

    @Override
    public void showQuestions(int position) {
        if (position == 0) {
            DataHolder.getInstance().setQuestionList(questionList);
        } else {
            List<Question.Response> tempList = new ArrayList<>();
            for (Question.Response response : questionList) {
                if (response.getCategory().equalsIgnoreCase(categoryList.get(position))) {
                    tempList.add(response);
                }
            }
            DataHolder.getInstance().setQuestionList(tempList);
        }
        CategoryFragment context = ((CategoryFragment) categoryView.get());
        Intent intent = new Intent(context.getActivity(), QuestionActivity.class);
        intent.putExtra("title", categoryList.get(position));
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(List<Question.Response> questionList) {
        if (categoryView.get() != null) {
            updateUI(questionList);
            categoryView.get().hideProgress();
        }
    }

    @Override
    public void onError(String error) {
        if (categoryView.get() != null) {
            categoryView.get().hideProgress();
            displayDataReloadAlert();
        }
    }

    @Override
    public void displayDataReloadAlert() {
        try {
            final CategoryFragment context = (CategoryFragment) categoryView.get();
            if (context.isAdded() && context.getActivity() != null) {
                new AlertDialog.Builder(context.getActivity())
                        .setTitle("Error")
                        .setMessage("Error receiving data from server, Reload Again...?")
                        .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                switch (context.getArguments().getInt("serviceType")) {
                                    case 1:
                                        prepareToFetchQuestion(1);
                                        break;

                                    case 2:
                                        prepareToFetchQuestion(2);
                                        break;

                                    case 3:
                                        prepareToFetchQuestion(3);
                                        break;
                                }

                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                context.getActivity().onBackPressed();
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

    @Override
    public void updateUI(List<Question.Response> responseList) {
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
    public CategoryAdapter initCategoryAdapter() {
        return categoryAdapter = new CategoryAdapter(categoryList, (CategoryFragment) categoryView.get());
    }
}
