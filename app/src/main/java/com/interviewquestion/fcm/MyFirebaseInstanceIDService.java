package com.interviewquestion.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.models.bean.UserRegistor;
import com.interviewquestion.network.RetrofitApiService;
import com.interviewquestion.network.RetrofitClient;
import com.interviewquestion.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 17/10/16.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

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
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        DataHolder.getInstance().getPreferences(this).edit().putString(Constant.DEVICE_TOKEN, token).apply();
        // if connected to internet

        RetrofitApiService apiService = RetrofitClient.getRetrofitClient();
        FirebaseMessaging.getInstance().subscribeToTopic(Constant.FCM_TOPIC_UPDATE_QUESTION);

        Call<UserRegistor> registrationCall = apiService.registerUserForFCM("a", token, Constant.ANDROID_DEVICE_TYPE);

        registrationCall.enqueue(new Callback<UserRegistor>() {
            @Override
            public void onResponse(Call<UserRegistor> call, Response<UserRegistor> response) {
                if (response.isSuccessful() && response.body().getStatus() == 1)
                    DataHolder.getInstance().getPreferences(MyFirebaseInstanceIDService.this).edit().
                            putBoolean(Constant.IS_USER_REGISTERED, true).apply();
            }

            @Override
            public void onFailure(Call<UserRegistor> call, Throwable t) {
                DataHolder.getInstance().getPreferences(MyFirebaseInstanceIDService.this).edit().
                        putBoolean(Constant.IS_USER_REGISTERED, false).apply();
            }
        });
    }

}
