package com.tech.quiz.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.tech.R;
import com.tech.quiz.billing.IabHelper;
import com.tech.quiz.billing.IabResult;
import com.tech.quiz.billing.Inventory;
import com.tech.quiz.billing.Purchase;
import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.interactor.SplashInterActorImpl;
import com.tech.quiz.models.bean.QuestionResponse;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.network.RetrofitApiService;
import com.tech.quiz.network.RetrofitClient;
import com.tech.quiz.repositories.interactor.SplashInterActor;
import com.tech.quiz.repositories.presenter.SplashPresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.HomeActivity;
import com.tech.quiz.view.activity.SplashActivity;
import com.tech.quiz.view.activity.SubscriptionDataActivity;
import com.tech.quiz.view.views.SplashView;

import java.util.ArrayList;
import java.util.List;

import library.mvp.ActivityPresenter;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * @author Sachin Narang
 */

public class SplashPresenterImpl extends ActivityPresenter<SplashView, SplashInterActor> implements SplashPresenter, SplashInterActor.OnIosQuestionResponseListener,
        SplashInterActor.OnAndroidQuestionResponseListener, SplashInterActor.OnJavaQuestionResponseListener {

    private int serviceCount;

    public SplashPresenterImpl(SplashView view, Context context) {
        super(view, context);
    }

    @Override
    public void prepareToFetchQuestion() {
        if (isViewAttached()) {
            SplashActivity context = (SplashActivity) getContext();
            if (context.isInternetAvailable()) {
                queryInventory();
                getView().showProgress();

                RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
                Observable<QuestionResponse> androidQuestion = apiService.getAndroidQuestion();
                Observable<QuestionResponse> javaQuestion = apiService.getJavaQuestion();
                Observable<QuestionResponse> iosQuestion = apiService.getIosQuestion();

                unSubscribeFromSubscriptionIfSubscribed(Constant.ANDROID_URL);
                unSubscribeFromSubscriptionIfSubscribed(Constant.IOS_URL);
                unSubscribeFromSubscriptionIfSubscribed(Constant.JAVA_URL);

                putSubscriberInMap(getInterActor().getAndroidQuestions(this, androidQuestion), Constant.ANDROID_URL);
                putSubscriberInMap(getInterActor().getIosQuestion(this, iosQuestion), Constant.IOS_URL);
                putSubscriberInMap(getInterActor().getJavaQuestions(this, javaQuestion), Constant.JAVA_URL);
            } else {
                context.onError(context.getString(R.string.error_internet_first_launch));
            }
        }
    }

    @Override
    public void onSuccess(List<QuestionResponse.Response> questionList, int serviceType) {
        serviceCount++;
        saveDataToDB(questionList, serviceType);
        if (serviceCount == 3) {
            goToHomeActivity();
            saveTimeToPreference();
        }
    }

    @Override
    public void onError(String error) {
        serviceCount--;
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
                        databaseManager.clearAndroidTableData();
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
                        databaseManager.clearIosTableData();
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
                        databaseManager.clearJavaTableData();
                        databaseManager.saveQuestionToJavaTable(javaList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Java java) {
                        javaList.add(java);
                    }
                });
    }

    @Override
    public void goToHomeActivity() {
        if (isViewAttached()) {
            DataHolder.getInstance().getPreferences(getContext()).edit().putBoolean(Constant.IS_APP_FIRST_LAUNCH, false).apply();
            getView().hideProgress();
            Intent intent = new Intent(getContext(), HomeActivity.class);
            getContext().startActivity(intent);
            ((SplashActivity) getContext()).finish();
        }
    }

    @Override
    public void queryInventory() {
        final IabHelper mHelper = new IabHelper(getContext(), Constant.BASE_64);

        final IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

                if (result.isFailure()) {
                    return;
                }

                SharedPreferences sharedPreferences = DataHolder.getInstance().getPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Purchase purchase = inventory.getPurchase(SubscriptionDataActivity.ITEM_SKU);

                if (purchase != null) {
                    editor.putBoolean(Constant.IS_SUBSCRIBED_USER, true);
                    editor.putLong(Constant.PURCHASE_TIME, purchase.getPurchaseTime());
                    editor.putString(Constant.TOKEN, purchase.getToken());
                    editor.putString(Constant.SKU, purchase.getSku());
                    editor.apply();

                }
                mHelper.dispose();
            }
        };

        // Create the helper, passing it our context and the public key to verify signatures with
        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);


        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    return;
                }

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    private void saveTimeToPreference() {
        if (isViewAttached())
            DataHolder.getInstance().getPreferences(getContext()).edit().
                    putLong(Constant.UPDATED_QUESTION_TIME_IN_MILLIS, System.currentTimeMillis()).apply();
    }

    @Override
    protected SplashInterActor createInterActor() {
        return new SplashInterActorImpl();
    }
}
