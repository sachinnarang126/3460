package com.tech.quiz.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.tech.R;
import com.tech.quiz.billing.IabHelper;
import com.tech.quiz.billing.IabResult;
import com.tech.quiz.billing.Inventory;
import com.tech.quiz.billing.Purchase;
import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.interactor.SplashInteractorImpl;
import com.tech.quiz.models.bean.QuestionResponse;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.network.RetrofitApiService;
import com.tech.quiz.network.RetrofitClient;
import com.tech.quiz.repositories.interactor.SplashInteractor;
import com.tech.quiz.repositories.presenter.SplashPresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.HomeActivity;
import com.tech.quiz.view.activity.SplashActivity;
import com.tech.quiz.view.activity.SubscriptionDataActivity;
import com.tech.quiz.view.views.SplashView;

import java.util.ArrayList;
import java.util.List;

import library.mvp.MvpBasePresenter;
import retrofit2.Call;

/**
 * Created by root on 28/9/16.
 */

public class SplashPresenterImpl extends MvpBasePresenter<SplashView> implements SplashPresenter, SplashInteractor.OnIosQuestionResponseListener,
        SplashInteractor.OnAndroidQuestionResponseListener, SplashInteractor.OnJavaQuestionResponseListener {

    private SplashInteractor splashInteractor;
    private int serviceCount;

    public SplashPresenterImpl(SplashView view, Context context) {
        attachView(view, context);
        splashInteractor = new SplashInteractorImpl();
    }

    @Override
    public void onCreate() {

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
        if (isViewAttached()) {
            SplashActivity context = (SplashActivity) getContext();
            if (context.isInternetAvailable()) {

                queryInventory();
                getView().showProgress();

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
                    System.out.println("you own this product");
                    System.out.println("purchase time in millis " + purchase.getPurchaseTime());
                    editor.putBoolean(Constant.IS_SUBSCRIBED_USER, true);
                    editor.putLong(Constant.PURCHASE_TIME, purchase.getPurchaseTime());
                    editor.putString(Constant.TOKEN, purchase.getToken());
                    editor.putString(Constant.SKU, purchase.getSku());
                    editor.apply();

                }
                mHelper.dispose();
            }
        };

        Log.d("SubscriptionData", "Creating IAB helper.");
        // Create the helper, passing it our context and the public key to verify signatures with
        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);


        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                System.out.println("setup finished");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    return;
                }

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d("SubscriptionData", "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    private void saveTimeToPreference() {
        if (isViewAttached())
            DataHolder.getInstance().getPreferences(getContext()).edit().
                    putLong(Constant.UPDATED_QUESTION_TIME_IN_MILLIS, System.currentTimeMillis()).apply();
    }
}
