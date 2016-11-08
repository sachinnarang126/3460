package com.tech.quiz.presenter;

import com.tech.R;
import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.interactor.HomeInteractorImpl;
import com.tech.quiz.models.bean.QuestionResponse;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.network.RetrofitApiService;
import com.tech.quiz.network.RetrofitClient;
import com.tech.quiz.repositories.interactor.HomeInteractor;
import com.tech.quiz.repositories.presenter.HomePresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.HomeActivity;
import com.tech.quiz.view.fragment.HomeFragment;
import com.tech.quiz.view.views.HomeView;

import java.util.ArrayList;
import java.util.List;

import library.mvp.MvpBasePresenter;
import retrofit2.Call;

/**
 * Created by root on 28/9/16.
 */

public class HomePresenterImpl extends MvpBasePresenter<HomeView> implements HomePresenter, HomeInteractor.OnIosQuestionResponseListener,
        HomeInteractor.OnAndroidQuestionResponseListener, HomeInteractor.OnJavaQuestionResponseListener {

    private HomeInteractor homeInteractor;

    public HomePresenterImpl(HomeView view) {
        attachView(view);
        homeInteractor = new HomeInteractorImpl();
    }

    @Override
    public void onCreate() {
        prepareToFetchQuestion();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        detachView();
    }

    @Override
    public void prepareToFetchQuestion() {
        if (hasToFetchQuestionFromServer() && isViewAttached()) {
            HomeFragment context = (HomeFragment) getView();
            if (((HomeActivity) context.getActivity()).isInternetAvailable()) {

                DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(context.getActivity());
                List<Integer> androidIdList = databaseManager.getAndroidIdList();
                List<Integer> iosIdList = databaseManager.getIosIdList();
                List<Integer> javaIdList = databaseManager.getJavaIdList();

//                System.out.println("androidIdList " + androidIdList);

//                System.out.println("iosIdList " + iosIdList);

//                System.out.println("javaIdList " + javaIdList);

                RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
                Call<QuestionResponse> androidQuestionCall;
                if (context.isServiceCallExist(Constant.ANDROID_POST_URL)) {
                    androidQuestionCall = context.getServiceCallIfExist(Constant.ANDROID_POST_URL);
                } else {
                    androidQuestionCall = apiService.getAndroidSelectedQuestion(androidIdList);
                    context.putServiceCallInServiceMap(androidQuestionCall, Constant.ANDROID_POST_URL);
                }

                Call<QuestionResponse> iosQuestionCall;
                if (context.isServiceCallExist(Constant.IOS_POST_URL)) {
                    iosQuestionCall = context.getServiceCallIfExist(Constant.IOS_POST_URL);
                } else {
                    iosQuestionCall = apiService.getIosSelectedQuestion(iosIdList);
                    context.putServiceCallInServiceMap(iosQuestionCall, Constant.IOS_POST_URL);
                }

                Call<QuestionResponse> javaQuestionCall;
                if (context.isServiceCallExist(Constant.JAVA_POST_URL)) {
                    javaQuestionCall = context.getServiceCallIfExist(Constant.JAVA_POST_URL);
                } else {
                    javaQuestionCall = apiService.getJavaSelectedQuestion(javaIdList);
                    context.putServiceCallInServiceMap(javaQuestionCall, Constant.JAVA_POST_URL);
                }

                homeInteractor.getAndroidQuestions(this, androidQuestionCall);
                homeInteractor.getIosQuestion(this, iosQuestionCall);
                homeInteractor.getJavaQuestions(this, javaQuestionCall);
            } else {
                context.onError(context.getString(R.string.error_internet_first_launch));
            }

        }
    }

    @Override
    public void onSuccess(List<QuestionResponse.Response> questionList, int serviceType) {
        saveDataToDB(questionList, serviceType);
        saveTimeToPreference();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    synchronized public void saveDataToDB(List<QuestionResponse.Response> questionList, int serviceType) {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(((HomeFragment) getView()).getActivity());
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

    @Override
    public void saveTimeToPreference() {
        DataHolder.getInstance().getPreferences(((HomeFragment) getView()).getActivity()).edit().
                putLong(Constant.UPDATED_QUESTION_TIME_IN_MILLIS, System.currentTimeMillis()).apply();
    }

    @Override
    public boolean hasToFetchQuestionFromServer() {
        long twoHour = 1000 * 60 * 60 * 2;
        long savedTime = DataHolder.getInstance().getPreferences(((HomeFragment) getView()).getActivity()).
                getLong(Constant.UPDATED_QUESTION_TIME_IN_MILLIS, 0);

        return System.currentTimeMillis() - savedTime > twoHour;
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
        databaseManager.saveQuestionToJavaTable(javaList);
    }
}
