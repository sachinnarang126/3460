package com.tech.quiz.fcm;

import android.provider.Settings;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tech.BuildConfig;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.models.bean.UserRegistor;
import com.tech.quiz.network.RetrofitApiService;
import com.tech.quiz.network.RetrofitClient;
import com.tech.quiz.util.Constant;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Sachin Narang
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }


    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    @SuppressWarnings("All")
    private void sendRegistrationToServer(String token) {
        DataHolder.getInstance().getPreferences(this).edit().putString(Constant.DEVICE_TOKEN, token).apply();
        // if connected to internet

        RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
        FirebaseMessaging.getInstance().subscribeToTopic(Constant.FCM_TOPIC_UPDATE_QUESTION);
        if (!BuildConfig.DEBUG) {
            String deviceID = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Observable<UserRegistor> registrationCall = apiService.registerUserForFCM(deviceID, token, Constant.ANDROID_DEVICE_TYPE);
            registrationCall.subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserRegistor>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    DataHolder.getInstance().getPreferences(MyFirebaseInstanceIDService.this).edit().
                            putBoolean(Constant.IS_USER_REGISTERED, false).apply();
                }

                @Override
                public void onNext(UserRegistor userRegistor) {
                    if (userRegistor.getStatus() == 1)
                        DataHolder.getInstance().getPreferences(MyFirebaseInstanceIDService.this).edit().
                                putBoolean(Constant.IS_USER_REGISTERED, true).apply();
                }
            });
        }
    }

}
