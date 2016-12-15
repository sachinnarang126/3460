package com.tech.quiz.presenter;

import android.content.Context;

import com.tech.R;
import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.interactor.HomeInterActorImpl;
import com.tech.quiz.models.bean.QuestionResponse;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.network.RetrofitApiService;
import com.tech.quiz.network.RetrofitClient;
import com.tech.quiz.repositories.interactor.HomeInterActor;
import com.tech.quiz.repositories.presenter.HomePresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.HomeActivity;
import com.tech.quiz.view.fragment.HomeFragment;
import com.tech.quiz.view.views.HomeView;

import java.util.ArrayList;
import java.util.List;

import library.mvp.BasePresenter;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * @author Sachin Narang
 */

public class HomePresenterImpl extends BasePresenter<HomeView> implements HomePresenter, HomeInterActor.OnIosQuestionResponseListener,
        HomeInterActor.OnAndroidQuestionResponseListener, HomeInterActor.OnJavaQuestionResponseListener {

    private HomeInterActor homeInteractor;

    public HomePresenterImpl(HomeView view, Context context) {
        attachView(view, context);
        homeInteractor = new HomeInterActorImpl();
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
            if (((HomeActivity) getContext()).isInternetAvailable()) {

                DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(context.getActivity());
                List<Integer> androidIdList = databaseManager.getAndroidIdList();
                List<Integer> iosIdList = databaseManager.getIosIdList();
                List<Integer> javaIdList = databaseManager.getJavaIdList();

                RetrofitApiService apiService = RetrofitClient.getRetrofitClient();

                Observable<QuestionResponse> androidQuestion = apiService.getAndroidSelectedQuestion(androidIdList);
                Observable<QuestionResponse> javaQuestion = apiService.getJavaSelectedQuestion(javaIdList);
                Observable<QuestionResponse> iosQuestion = apiService.getIosSelectedQuestion(iosIdList);

                context.unSubscribeFromSubscriptionIfSubscribed(Constant.ANDROID_POST_URL);
                context.unSubscribeFromSubscriptionIfSubscribed(Constant.IOS_POST_URL);
                context.unSubscribeFromSubscriptionIfSubscribed(Constant.JAVA_POST_URL);

                context.putSubscriberInMap(homeInteractor.getAndroidQuestions(this, androidQuestion), Constant.ANDROID_POST_URL);
                context.putSubscriberInMap(homeInteractor.getIosQuestion(this, iosQuestion), Constant.IOS_POST_URL);
                context.putSubscriberInMap(homeInteractor.getJavaQuestions(this, javaQuestion), Constant.JAVA_POST_URL);

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
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(getContext());
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
        DataHolder.getInstance().getPreferences(getContext()).edit().
                putLong(Constant.UPDATED_QUESTION_TIME_IN_MILLIS, System.currentTimeMillis()).apply();
    }

    @Override
    public boolean hasToFetchQuestionFromServer() {
        long twoHour = 1000 * 60 * 60 * 2;
        long savedTime = DataHolder.getInstance().getPreferences(getContext()).
                getLong(Constant.UPDATED_QUESTION_TIME_IN_MILLIS, 0);

        return System.currentTimeMillis() - savedTime > twoHour;
    }

    private void saveAndroidQuestion(final DatabaseManager databaseManager, List<QuestionResponse.Response> questionList) {
        final List<Android> androidList = new ArrayList<>();

        Observable.from(questionList).
                map(new Func1<QuestionResponse.Response, Android>() {
                    @Override
                    public Android call(QuestionResponse.Response question) {
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
                        return android;
                    }
                }).
                subscribe(new Observer<Android>() {
                    @Override
                    public void onCompleted() {
                        databaseManager.saveQuestionToAndroidTable(androidList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Android android) {
                        androidList.add(android);
                    }
                });
    }

    private void saveIosQuestion(final DatabaseManager databaseManager, List<QuestionResponse.Response> questionList) {
        final List<Ios> iosList = new ArrayList<>();
        Observable.from(questionList).
                map(new Func1<QuestionResponse.Response, Ios>() {
                    @Override
                    public Ios call(QuestionResponse.Response question) {
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
                        return ios;
                    }
                }).
                subscribe(new Observer<Ios>() {
                    @Override
                    public void onCompleted() {
                        databaseManager.saveQuestionToIosTable(iosList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Ios ios) {
                        iosList.add(ios);
                    }
                });
    }

    private void saveJavaQuestion(final DatabaseManager databaseManager, List<QuestionResponse.Response> questionList) {
        final List<Java> javaList = new ArrayList<>();
        Observable.from(questionList).
                map(new Func1<QuestionResponse.Response, Java>() {
                    @Override
                    public Java call(QuestionResponse.Response question) {
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
                        return java;
                    }
                }).
                subscribe(new Observer<Java>() {
                    @Override
                    public void onCompleted() {
                        databaseManager.saveQuestionToJavaTable(javaList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Java java) {
                        javaList.add(java);
                    }
                });
    }
}
