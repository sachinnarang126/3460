package com.interviewquestion.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import com.finart.R;
import com.finart.api.GetActivationDetail;
import com.finart.api.RegisterDeviceTokenApi;
import com.finart.dataholder.DataHolder;
import com.finart.interfaces.ResponseListener;
import com.finart.util.Constants;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationIntentService extends IntentService implements ResponseListener {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"newTemplate", "newCategoryMapping", "newAppVersion"};
//    private static RegistrationIntentService instance = null;

    public RegistrationIntentService() {
        super(TAG);
    }

    /*public static boolean isInstanceCreated() {
        return instance != null;
    }*/

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);
            DataHolder.getInstance().getPreferences(this).edit().
                    putString(Constants.DEVICE_TOKEN, token).apply();

            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);

            String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            new GetActivationDetail(this, this).prepareApiRequest(deviceId);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
//            sharedPreferences.edit().putBoolean(Constants.SENT_TOKEN_TO_SERVER, true).apply();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(Constants.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        /*Intent registrationComplete = new Intent(Constants.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);*/
    }

    @Override
    public void onCreate() {
//        instance = this;
        super.onCreate();
    }

    @Override
    public void onDestroy() {
//        instance = null;
        super.onDestroy();
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        new RegisterDeviceTokenApi(this, this).prepareApiRequest(token);
        SharedPreferences sharedPreferences = DataHolder.getInstance().getPreferences(this);
        /*if (!sharedPreferences.getBoolean(Constants.IS_SUBSCRIBED_USER, false)) {
            new GetActivationDetail(this, this).prepareApiRequest(Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID));
        }*/
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

    @Override
    public void onSuccess(int identifyResponse) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(Constants.SENT_TOKEN_TO_SERVER, true).apply();
    }

    @Override
    public void onError(String message) {
        System.out.println("Error " + message);
    }
}
