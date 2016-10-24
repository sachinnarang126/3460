package com.tech.quiz.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.util.Constant;

/**
 * Created by sachin on 10/10/16.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isAppFirstLaunch = DataHolder.getInstance().getPreferences(context).
                getBoolean(Constant.IS_APP_FIRST_LAUNCH, true);

        if (isAppFirstLaunch) {

        }
    }
}
