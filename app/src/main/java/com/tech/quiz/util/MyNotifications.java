package com.tech.quiz.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.tech.R;
import com.tech.quiz.view.activity.SplashActivity;

import java.util.List;

public class MyNotifications {

    public void sendBigLayoutNotification(List<String> dataInformation, Context context, String title) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 4444, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.android_bg)
                .setContentTitle("Tech Quiz")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setContentText(title);
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Unanswered Question Found");
        inboxStyle.setSummaryText("Do you want to answer them?");
        for (String str : dataInformation) {
            inboxStyle.addLine(str);
        }

        // Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(11102, mBuilder.build());
    }
}
