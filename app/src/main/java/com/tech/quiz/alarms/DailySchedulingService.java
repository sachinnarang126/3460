package com.tech.quiz.alarms;

import android.app.IntentService;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.util.MyNotifications;

import java.util.ArrayList;
import java.util.List;


public class DailySchedulingService extends IntentService {

    public DailySchedulingService() {
        super("DailySchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (DataHolder.getInstance().getHomeActivityInstance() == null)
            dailySchedulingTask();

        // Release the wake lock provided by the BroadcastReceiver.
        DailyAlarmReceiver.completeWakefulIntent(intent);
    }

    /**
     * This function is called daily at 5:00 PM
     */
    private void dailySchedulingTask() {
        try {
            boolean prefReceiveNotification = PreferenceManager
                    .getDefaultSharedPreferences(this).getBoolean("prefReceiveNotification", true);
            if (prefReceiveNotification) {
                List<String> dataInformation = getDataInformationFromDB();
                if (dataInformation.size() > 0) {
                    String title = "Following Unanswered question found";
                    MyNotifications myNotifications = new MyNotifications();
                    myNotifications.sendBigLayoutNotification(dataInformation, this, title);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getDataInformationFromDB() {

        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(this);
        long unansweredAndroidQuestion = databaseManager.getUnansweredAndroidQuestionCount();
        long unansweredIosQuestion = databaseManager.getUnansweredIosQuestionCount();
        long unansweredJavaQuestion = databaseManager.getUnansweredJavaQuestionCount();

        List<String> dataInformation = new ArrayList<>();

        if (unansweredAndroidQuestion > 0) {
            dataInformation.add("Android Question: " + unansweredAndroidQuestion);
        }

        if (unansweredIosQuestion > 0) {
            dataInformation.add("Ios Question: " + unansweredIosQuestion);
        }

        if (unansweredJavaQuestion > 0) {
            dataInformation.add("Java Question: " + unansweredJavaQuestion);
        }

        return dataInformation;
    }

}
