package com.interviewquestion.alarms;

import android.app.IntentService;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.interviewquestion.databasemanager.DatabaseManager;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.util.MyNotifications;


public class DailySchedulingService extends IntentService {

    public DailySchedulingService() {
        super("DailySchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (DataHolder.getInstance().getInstance() == null)
            dailySchedulingTask();

        // Release the wake lock provided by the BroadcastReceiver.
        DailyAlarmReceiver.completeWakefulIntent(intent);
    }

    /**
     * This function is called daily at 9:00 PM
     */
    private void dailySchedulingTask() {
        try {
            /*Calendar calendar = Calendar.getInstance();
            int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);*/
            boolean prefReceiveNotification = PreferenceManager
                    .getDefaultSharedPreferences(this).getBoolean("prefReceiveNotification", true);
            if (prefReceiveNotification) {
                String dataInformation = getDataInformationFromDB();
                if (!dataInformation.isEmpty()) {
                    dataInformation = "Following Unanswered question found \n" + dataInformation + "\n Do you want to answer them?";
                    MyNotifications myNotifications = new MyNotifications();
                    myNotifications.sendNotification(dataInformation, this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDataInformationFromDB() {

        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(this);
        long unansweredAndroidQuestion = databaseManager.getUnansweredAndroidQuestionCount();
        long unansweredIosQuestion = databaseManager.getUnansweredIosQuestionCount();
        long unansweredJavaQuestion = databaseManager.getUnansweredJavaQuestionCount();

        String dataInformation = "";

        if (unansweredAndroidQuestion > 0) {
            dataInformation = "Android Question " + unansweredAndroidQuestion + "\n";
        }

        if (unansweredIosQuestion > 0) {
            dataInformation = dataInformation + "Ios Question " + unansweredIosQuestion + "\n";
        }

        if (unansweredJavaQuestion > 0) {
            dataInformation = dataInformation + "Java Question " + unansweredJavaQuestion + "\n";
        }

        return dataInformation;
    }

}
