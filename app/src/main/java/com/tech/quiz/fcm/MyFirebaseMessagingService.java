package com.tech.quiz.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tech.quiz.presenter.UpdateQuestionPresenterImpl;

import org.json.JSONArray;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        /*if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/

        if (remoteMessage.getFrom().equalsIgnoreCase("/topics/update_question")) {
            // 1 android, 2 ios, 3 java
            if (remoteMessage.getData().size() > 0) {
                try {
                    int technology = Integer.parseInt(remoteMessage.getData().get("technology"));
                    UpdateQuestionPresenterImpl presenter = new UpdateQuestionPresenterImpl(this);
                    presenter.parseJson(new JSONArray(remoteMessage.getData().get("response")), technology);
                    /*JSONObject json = new JSONObject(remoteMessage.getData().toString());
                    System.out.println("payload " + json);*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]
}
