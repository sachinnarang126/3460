package com.tech.quiz.appcontroller;

import android.app.Application;

import com.tech.quiz.alarms.DailyAlarmReceiver;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.util.Constant;

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
