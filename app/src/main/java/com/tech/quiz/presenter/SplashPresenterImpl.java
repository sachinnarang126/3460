package com.tech.quiz.presenter;

import android.content.Context;
import android.content.Intent;

import com.tech.R;
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
import com.tech.quiz.view.views.SplashView;

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
        if (serviceCount == 3) {
            goToHomeActivity();
            saveTimeToPreference();
        }
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

    @Override
    public void queryInventory() {

        /*final IabHelper mHelper = new IabHelper(this, Constant.base64EncodedPublicKey);

        final IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                Log.d(TAG, "Query inventory finished.");
                // Is it a failure?
                if (result.isFailure()) {
                    return;
                }

                Log.d(TAG, "Query inventory was successful.");

                SharedPreferences sharedPreferences = DataHolder.getInstance().getPreferences(SplashActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Purchase purchase = inventory.getPurchase("one_year");
                String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                System.out.println("deviceId " + deviceId);

                if (purchase != null) {
                    System.out.println("you own this product");
                    System.out.println("purchase time in millis " + purchase.getPurchaseTime());
                    editor.putBoolean(Constants.IS_SUBSCRIBED_USER, true);
                    editor.putLong(Constants.PURCHASE_TIME, purchase.getPurchaseTime());
                    editor.putString(Constants.TOKEN, purchase.getToken());
                    editor.putString(Constants.SKU, purchase.getSku());
                    editor.apply();

                    //incase google return old subscription detail we should allow only after
                    //checking the validity
                    long purchaseDateInMillis = sharedPreferences.getLong(Constants.PURCHASE_TIME, 0);
                    Calendar calendar = Calendar.getInstance();
                    long currentTimeInMillis = calendar.getTimeInMillis();
                    calendar.setTimeInMillis(purchaseDateInMillis);
                    calendar.add(Calendar.YEAR, 1);
                    calendar.add(Calendar.DAY_OF_YEAR, 1); // add a day to handle time zone issue

                    editor.putBoolean(Constants.IS_AUTHORISED_USER, currentTimeInMillis <= calendar.getTimeInMillis()).apply();

                    // user is subscribed user, and storing the product detail on server
                    new StoreActivationDetail(SplashActivity.this, SplashActivity.this).prepareApiRequest(deviceId);

                } else {
                    System.out.println("you don't own this product");
                    // here user is not authorised so we are getting the activation detail from server if exists
                    // if detail is not exist on server then saving the details on the server
                    new GetActivationDetail(SplashActivity.this, SplashActivity.this).prepareApiRequest(deviceId);
                }

            }
        };

        Log.d("SubscriptionData", "Creating IAB helper.");
        // Create the helper, passing it our context and the public key to verify signatures with
        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);


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
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });*/
    }

    private void saveTimeToPreference() {
        DataHolder.getInstance().getPreferences((SplashActivity) splashView.get()).edit().
                putLong(Constant.UPDATED_QUESTION_TIME_IN_MILLIS, System.currentTimeMillis()).apply();
    }
}
