package com.tech.quiz.databasemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.SplashActivity;

import java.sql.SQLException;

/**
 * @author Sachin Narang
 */

class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // Name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "IQ_DB";

    // Any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 2; // update shared preference for authorized user flag

    private Dao<Java, Integer> javaDao;
    private Dao<Ios, Integer> iosDao;
    private Dao<Android, Integer> androidDao;
    private Context context;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Android.class);
            TableUtils.createTable(connectionSource, Ios.class);
            TableUtils.createTable(connectionSource, Java.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        if (oldVersion == 1 && newVersion == 2) {
            try {
                TableUtils.dropTable(connectionSource, Android.class, true);
                TableUtils.dropTable(connectionSource, Ios.class, true);
                TableUtils.dropTable(connectionSource, Java.class, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            onCreate(sqLiteDatabase, connectionSource);
            DataHolder.getInstance().getPreferences(context).edit().putBoolean(Constant.IS_APP_FIRST_LAUNCH, true).apply();
            restartApp();
        }

    /*
        try {
            if(oldVersion == 6 && newVersion == 7) {
                sqLiteDatabase.execSQL("ALTER TABLE `TEMPSMS` ADD COLUMN isSmsParsed BOOLEAN;");
            } else {
            TableUtils.dropTable(connectionSource, GraphModel.class, true);
            TableUtils.dropTable(connectionSource, Transaction.class, true);
            TableUtils.dropTable(connectionSource, CashInFlow.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, TempSms.class, true);
            TableUtils.dropTable(connectionSource, SubCategory.class, true);
            TableUtils.dropTable(connectionSource, Template.class, true);
            TableUtils.dropTable(connectionSource, Accounts.class, true);
            TableUtils.dropTable(connectionSource, MerchantMapping.class, true);
            TableUtils.dropTable(connectionSource, Transfers.class, true);
            TableUtils.dropTable(connectionSource, Alarms.class, true);
            TableUtils.dropTable(connectionSource, Bills.class, true);

                onCreate(sqLiteDatabase, connectionSource);

            DataHolder.getInstance().getPreferences(context).edit().
                    putBoolean(Constants.IS_APP_FIRST_LAUNCH, true).apply();
            DataHolder.getInstance().getPreferences(context).edit().
                    putBoolean(Constants.IS_SETUP_RUNNING, false).apply();
            Utils.restartApp(context);
            }
*/
/*

            sqLiteDatabase.execSQL("ALTER TABLE `GRAPHMODEL` RENAME TO GRAPHMODEL_TB;");
            sqLiteDatabase.execSQL("ALTER TABLE `GRAPHMODEL_TB` ADD COLUMN percentage INTEGER;");
            sqLiteDatabase.execSQL("ALTER TABLE `TEMPSMS` ADD COLUMN status TEXT;");
            sqLiteDatabase.execSQL("ALTER TABLE `TEMPSMS` ADD COLUMN errorCode TEXT;");
            sqLiteDatabase.execSQL("ALTER TABLE `CASHINFLOW` RENAME TO CASHINFLOW_TB;");
            sqLiteDatabase.execSQL("ALTER TABLE `CASHINFLOW_TB` ADD COLUMN sms_time_stamp LONG;");
            sqLiteDatabase.execSQL("ALTER TABLE `TRANSACTION` RENAME TO TRANSACTION_TB;");
*//*

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }


    private void restartApp() {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    Dao<Android, Integer> getAndroidDao() {
        if (androidDao == null) {
            try {
                androidDao = getDao(Android.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return androidDao;
    }

    Dao<Ios, Integer> getIosDao() {
        if (iosDao == null) {
            try {
                iosDao = getDao(Ios.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return iosDao;
    }

    Dao<Java, Integer> getJavaDao() {
        if (javaDao == null) {
            try {
                javaDao = getDao(Java.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return javaDao;
    }


    /*public void clearAllTableData() {
        try {
            TableUtils.clearTable(connectionSource, Java.class);
            TableUtils.clearTable(connectionSource, Ios.class);
            TableUtils.clearTable(connectionSource, Android.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void close() {
        super.close();
        androidDao = null;
        iosDao = null;
        androidDao = null;
    }
}
