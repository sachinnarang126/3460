package com.interviewquestion.presenter;

import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.fragment.CategoryFragment;
import com.interviewquestion.interactor.QuestionInteractor;
import com.interviewquestion.interactor.QuestionInteractorImpl;
import com.interviewquestion.network.RetrofitApiService;
import com.interviewquestion.network.RetrofitClient;
import com.interviewquestion.repository.Question;
import com.interviewquestion.util.Constant;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;

/**
 * Created by root on 28/9/16.
 */

public class QuestionPresenterImpl implements QuestionPresenter, QuestionInteractor.OnQuestionResponseListener {

    private WeakReference<CategoryFragment> questionView;
    private QuestionInteractor questionInteractor;

    public QuestionPresenterImpl(WeakReference<CategoryFragment> questionView) {
        this.questionView = questionView;
        questionInteractor = new QuestionInteractorImpl();
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
                        questionInteractor.getAndroidQuestions(this, questionCall);
                        break;

                    case 2:
                        if (questionView.get().isServiceCallExist(Constant.IOS)) {
                            questionCall = questionView.get().getServiceCallIfExist(Constant.IOS);
                        } else {
                            questionCall = apiService.getIosQuestion();
                            questionView.get().putServiceCallInServiceMap(questionCall, Constant.IOS);
                        }
                        questionInteractor.getIosQuestion(this, questionCall);
                        break;

                    case 3:
                        if (questionView.get().isServiceCallExist(Constant.JAVA)) {
                            questionCall = questionView.get().getServiceCallIfExist(Constant.JAVA);
                        } else {
                            questionCall = apiService.getJavaQuestion();
                            questionView.get().putServiceCallInServiceMap(questionCall, Constant.JAVA);
                        }
                        questionInteractor.getJavaQuestions(this, questionCall);
                        break;
                }
            }

        }
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
        }
    }
}
