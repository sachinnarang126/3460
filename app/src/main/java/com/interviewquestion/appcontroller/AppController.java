package com.interviewquestion.appcontroller;

import android.app.Application;

import com.interviewquestion.dataholder.DataHolder;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataHolder.getInstance();
    }
}
