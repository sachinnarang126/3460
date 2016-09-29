package com.interviewquestion.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.activity.QuestionActivity;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.fragment.CategoryFragment;
import com.interviewquestion.interactor.CategoryInteractor;
import com.interviewquestion.interactor.CategoryInteractorImpl;
import com.interviewquestion.network.RetrofitApiService;
import com.interviewquestion.network.RetrofitClient;
import com.interviewquestion.repository.Question;
import com.interviewquestion.util.Constant;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by root on 28/9/16.
 */

public class CategoryPresenterImpl implements CategoryPresenter, CategoryInteractor.OnQuestionResponseListener {

    private WeakReference<CategoryFragment> questionView;
    private CategoryInteractor categoryInteractor;

    public CategoryPresenterImpl(WeakReference<CategoryFragment> questionView) {
        this.questionView = questionView;
        categoryInteractor = new CategoryInteractorImpl();
    }

    @Override
    public void onDestroy() {
        questionView = null;
    }

    @Override
    public void prepareToFetchQuestion(int serviceType) {
        if (questionView.get() != null) {
            if (((HomeActivity) questionView.get().getActivity()).isInternetAvailable()) {

                questionView.get().showProgress();
                RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
                Call<Question> questionCall;
                switch (serviceType) {
                    case 1:
                        if (questionView.get().isServiceCallExist(Constant.ANDROID)) {
                            questionCall = questionView.get().getServiceCallIfExist(Constant.ANDROID);
                        } else {
                            questionCall = apiService.getAndroidQuestion();
                            questionView.get().putServiceCallInServiceMap(questionCall, Constant.ANDROID);
                        }
                        categoryInteractor.getAndroidQuestions(this, questionCall);
                        break;

                    case 2:
                        if (questionView.get().isServiceCallExist(Constant.IOS)) {
                            questionCall = questionView.get().getServiceCallIfExist(Constant.IOS);
                        } else {
                            questionCall = apiService.getIosQuestion();
                            questionView.get().putServiceCallInServiceMap(questionCall, Constant.IOS);
                        }
                        categoryInteractor.getIosQuestion(this, questionCall);
                        break;

                    case 3:
                        if (questionView.get().isServiceCallExist(Constant.JAVA)) {
                            questionCall = questionView.get().getServiceCallIfExist(Constant.JAVA);
                        } else {
                            questionCall = apiService.getJavaQuestion();
                            questionView.get().putServiceCallInServiceMap(questionCall, Constant.JAVA);
                        }
                        categoryInteractor.getJavaQuestions(this, questionCall);
                        break;
                }
            }

        }
    }

    @Override
    public void showQuestions(int position, String category, List<Question.Response> questionList) {
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

        Intent intent = new Intent(questionView.get().getActivity(), QuestionActivity.class);
        intent.putExtra("title", category);
        questionView.get().startActivity(intent);
    }

    @Override
    public void onSuccess(List<Question.Response> questionList) {
        if (questionView.get() != null) {
            questionView.get().onSuccess(questionList);
            questionView.get().hideProgress();
        }
    }

    @Override
    public void onError(String error) {
        if (questionView.get() != null) {
            questionView.get().hideProgress();
            questionView.get().onError(error);
            displayDataReloadAlert();
        }
    }

    @Override
    public void displayDataReloadAlert() {
        try {
            if (questionView.get().isAdded() && questionView.get().getActivity() != null) {
                new AlertDialog.Builder(questionView.get().getActivity())
                        .setTitle("Error")
                        .setMessage("Error receiving data from server, Reload Again...?")
                        .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                switch (questionView.get().getArguments().getInt("serviceType")) {
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
                                questionView.get().getActivity().onBackPressed();
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
