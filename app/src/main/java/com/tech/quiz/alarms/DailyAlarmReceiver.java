package com.tech.quiz.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;

public class DailyAlarmReceiver extends WakefulBroadcastReceiver {

    // The app's AlarmManager, which provides access to the system alarm services.
//    private AlarmManager alarmMgr;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Intent service = new Intent(context, DailySchedulingService.class);
            // Start the service, keeping the device awake while it is launching.
            startWakefulService(context, service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a repeating alarm that runs once a day at approximately 5:00 pm . When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     *
     * @param context alarm
     */

    public void setAlarm(Context context) {

        try {
            Calendar calendar = Calendar.getInstance();
            /*calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.MINUTE, 1);*/
            calendar.roll(Calendar.MINUTE, 1);

            long todayTimeInMillis = calendar.getTimeInMillis();

            long intervalTimeInMillis = 1000 * 20;// * 60 * 24; // 1 day
            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, DailyAlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, todayTimeInMillis, intervalTimeInMillis, alarmIntent);
//            alarmMgr.set(AlarmManager.RTC_WAKEUP, todayTime, alarmIntent);

            // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
            // device is rebooted.
            ComponentName receiver = new ComponentName(context, PhoneBootReceiver.class);
            PackageManager pm = context.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void cancelAlarm(Context context) {

        try {
            // If the alarm has been set, cancel it.
            if (alarmMgr != null) {
                alarmMgr.cancel(alarmIntent);
            }

            // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
            // alarm when the device is rebooted.
            ComponentName receiver = new ComponentName(context, PhoneBootReceiver.class);
            PackageManager pm = context.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
