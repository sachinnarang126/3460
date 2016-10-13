package com.interviewquestion.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String values = data.getString("values");
        System.out.println("from: " + from);
        System.out.println("values: " + values);

        if (from.startsWith("/topics/newCategoryMapping")) {
            // message received from some topic.

        } else if (from.startsWith("/topics/newTemplate")) {
            // message received from some topic.

        } else if (from.startsWith("/topics/newAppVersion")) {
            // message received from some topic.

        }
    }
}
