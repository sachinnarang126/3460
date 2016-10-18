package com.interviewquestion.presenter;

import android.content.Context;
import android.content.Intent;

import com.interviewquestion.R;
import com.interviewquestion.activity.HomeActivity;
import com.interviewquestion.activity.SplashActivity;
import com.interviewquestion.databasemanager.DatabaseManager;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.interactor.SplashInteractorImpl;
import com.interviewquestion.models.bean.QuestionResponse;
import com.interviewquestion.models.databasemodel.Android;
import com.interviewquestion.models.databasemodel.Ios;
import com.interviewquestion.models.databasemodel.Java;
import com.interviewquestion.network.RetrofitApiService;
import com.interviewquestion.network.RetrofitClient;
import com.interviewquestion.repositories.interactor.SplashInteractor;
import com.interviewquestion.repositories.presenter.SplashPresenter;
import com.interviewquestion.util.Constant;
import com.interviewquestion.view.SplashView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by root on 28/9/16.
 */

public class SplashPresenterImpl implements SplashPresenter, SplashInteractor.OnIosQuestionResponseListener,
        SplashInteractor.OnAndroidQuestionResponseListener, SplashInteractor.OnJavaQuestionResponseListener {

    private WeakReference<SplashView> splashView;
    private SplashInteractor splashInteractor;
    private int serviceCount;

    public SplashPresenterImpl(WeakReference<SplashView> splashView) {
        this.splashView = splashView;
        splashInteractor = new SplashInteractorImpl();
    }

    @Override
    public void onDestroy() {
        splashView.clear();
    }

    @Override
    public void prepareToFetchQuestion() {
        if (splashView.get() != null) {
            SplashActivity context = (SplashActivity) splashView.get();
            if (context.isInternetAvailable()) {

                splashView.get().showProgress();
                RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
                Call<QuestionResponse> androidQuestionCall;
                if (context.isServiceCallExist(Constant.ANDROID_URL)) {
                    androidQuestionCall = context.getServiceCallIfExist(Constant.ANDROID_URL);
                } else {
                    androidQuestionCall = apiService.getAndroidQuestion();
                    context.putServiceCallInServiceMap(androidQuestionCall, Constant.ANDROID_URL);
                }


                Call<QuestionResponse> iosQuestionCall;
                if (context.isServiceCallExist(Constant.IOS_URL)) {
                    iosQuestionCall = context.getServiceCallIfExist(Constant.IOS_URL);
                } else {
                    iosQuestionCall = apiService.getIosQuestion();
                    context.putServiceCallInServiceMap(iosQuestionCall, Constant.IOS_URL);
                }

                Call<QuestionResponse> javaQuestionCall;
                if (context.isServiceCallExist(Constant.JAVA_URL)) {
                    javaQuestionCall = context.getServiceCallIfExist(Constant.JAVA_URL);
                } else {
                    javaQuestionCall = apiService.getJavaQuestion();
                    context.putServiceCallInServiceMap(javaQuestionCall, Constant.JAVA_URL);
                }

                splashInteractor.getAndroidQuestions(this, androidQuestionCall);
                splashInteractor.getIosQuestion(this, iosQuestionCall);
                splashInteractor.getJavaQuestions(this, javaQuestionCall);
            } else {
                context.onError(context.getString(R.string.error_internet_first_launch));
            }

        }
    }

    @Override
    public void onSuccess(List<QuestionResponse.Response> questionList, int serviceType) {
        serviceCount++;
        saveDataToDB(questionList, serviceType);
        if (serviceCount == 3)
            goToHomeActivity();
    }

    @Override
    public void onError(String error) {
        serviceCount--;
//        displayDataReloadAlert();
    }

    @Override
    synchronized public void saveDataToDB(List<QuestionResponse.Response> questionList, int serviceType) {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager((SplashActivity) splashView.get());
        switch (serviceType) {
            case Constant.ANDROID:
                saveAndroidQuestion(databaseManager, questionList);
                break;

            case Constant.IOS:
                saveIosQuestion(databaseManager, questionList);
                break;

            case Constant.JAVA:
                saveJavaQuestion(databaseManager, questionList);
                break;
        }
    }

    private void saveAndroidQuestion(DatabaseManager databaseManager, List<QuestionResponse.Response> questionList) {
        List<Android> androidList = new ArrayList<>();
        for (QuestionResponse.Response question : questionList) {
            Android android = new Android();
            android.setQuestionId(Integer.parseInt(question.getId()));
            android.setUserLevel(Integer.parseInt(question.getUserLevel()));
            android.setCategory(question.getCategory());
            android.setQuestion(question.getQuestion());
            android.setA(question.getA());
            android.setB(question.getB());
            android.setC(question.getC());
            android.setD(question.getD());
            android.setAnswer(question.getAnswer());
            androidList.add(android);
        }
        databaseManager.clearAndroidTableData();
        databaseManager.saveQuestionToAndroidTable(androidList);

    }

    private void saveIosQuestion(DatabaseManager databaseManager, List<QuestionResponse.Response> questionList) {
        List<Ios> iosList = new ArrayList<>();

        for (QuestionResponse.Response question : questionList) {
            Ios ios = new Ios();
            ios.setQuestionId(Integer.parseInt(question.getId()));
            ios.setUserLevel(Integer.parseInt(question.getUserLevel()));
            ios.setCategory(question.getCategory());
            ios.setQuestion(question.getQuestion());
            ios.setA(question.getA());
            ios.setB(question.getB());
            ios.setC(question.getC());
            ios.setD(question.getD());
            ios.setAnswer(question.getAnswer());
            iosList.add(ios);
        }
        databaseManager.clearIosTableData();
        databaseManager.saveQuestionToIosTable(iosList);

    }

    private void saveJavaQuestion(DatabaseManager databaseManager, List<QuestionResponse.Response> questionList) {
        List<Java> javaList = new ArrayList<>();

        for (QuestionResponse.Response question : questionList) {
            Java java = new Java();
            java.setQuestionId(Integer.parseInt(question.getId()));
            java.setUserLevel(Integer.parseInt(question.getUserLevel()));
            java.setCategory(question.getCategory());
            java.setQuestion(question.getQuestion());
            java.setA(question.getA());
            java.setB(question.getB());
            java.setC(question.getC());
            java.setD(question.getD());
            java.setAnswer(question.getAnswer());
            javaList.add(java);
        }
        databaseManager.clearJavaTableData();
        databaseManager.saveQuestionToJavaTable(javaList);

    }

    @Override
    public void goToHomeActivity() {
        Context context = (SplashActivity) splashView.get();
        DataHolder.getInstance().getPreferences(context).edit().putBoolean(Constant.IS_APP_FIRST_LAUNCH, false).apply();
        splashView.get().hideProgress();
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
        ((SplashActivity) context).finish();
    }
}
