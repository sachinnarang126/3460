package com.interviewquestion.appcontroller;

import android.app.Application;

import com.interviewquestion.alarms.DailyAlarmReceiver;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.util.Constant;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (!DataHolder.getInstance().getPreferences(this).getBoolean(Constant.IS_ALARM_ACTIVATED, false)) {
            new DailyAlarmReceiver().setAlarm(this);
            DataHolder.getInstance().getPreferences(this).edit().
                    putBoolean(Constant.IS_ALARM_ACTIVATED, true).apply();
        }
    }
}
