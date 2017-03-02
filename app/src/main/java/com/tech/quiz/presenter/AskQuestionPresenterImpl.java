package com.tech.quiz.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.tech.R;
import com.tech.quiz.interactor.AskQuestionInterActorImpl;
import com.tech.quiz.models.bean.AskQuestionResponse;
import com.tech.quiz.network.RetrofitApiService;
import com.tech.quiz.network.RetrofitClient;
import com.tech.quiz.repositories.interactor.AskQuestionInterActor;
import com.tech.quiz.repositories.presenter.AskQuestionPresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.views.AskQuestionView;

import library.mvp.ActivityPresenter;

/**
 * @author Sachin Narang
 */

public class AskQuestionPresenterImpl extends ActivityPresenter<AskQuestionView, AskQuestionInterActor> implements AskQuestionPresenter, AskQuestionInterActor.OnAskQuestionResponseListener {

    private ProgressDialog pDialog;

    public AskQuestionPresenterImpl(AskQuestionView discussionDetailView, Context context) {
        super(discussionDetailView, context);
    }

    @Override
    protected AskQuestionInterActor createInterActor() {
        return new AskQuestionInterActorImpl();
    }

    @Override
    public void onSuccess(AskQuestionResponse askQuestionResponse) {
        dismissProgressBar();
        if (askQuestionResponse.getStatus() == 1) {
            getView().onError(askQuestionResponse.getMessage());
            getView().resetToDefaultValue();
        } else {
            getView().onError(askQuestionResponse.getMessage());
        }
    }

    @Override
    public void onError(String error) {
        dismissProgressBar();
        getView().onError(error);
    }

    @Override
    public void createAskQuestionApi(String technology, String email, String question) {
        if (getActivity().isInternetAvailable()) {
            String error = validateData(technology, email, question);
            if (error.isEmpty()) {
                getActivity().hideSoftKeyboard();
                showProgressBar();
                technology = technology.replace("'", "\\'");
                question = question.replace("'", "\\'");
                unSubscribeFromSubscriptionIfSubscribed(Constant.ASK_QUESTION_URL);
                RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
                putSubscriberInMap(getInterActor().askQuestion(this, apiService.askQuestion(technology, email, question)),
                        Constant.ASK_QUESTION_URL);
            } else {
                getView().onError(error);
            }
        } else {
            getView().onError(getString(R.string.error_internet));
        }
    }

    private String validateData(String technology, String email, String question) {
        if (technology.isEmpty()) {
            return getString(R.string.error_technology);
        } else if (!isValidEmail(email)) {
            return getString(R.string.error_email);
        } else if (question.isEmpty()) {
            return getString(R.string.error_question);
        }
        return "";
    }

    private void showProgressBar() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("loading....");
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }

    private void dismissProgressBar() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
