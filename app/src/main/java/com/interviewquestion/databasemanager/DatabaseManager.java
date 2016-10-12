package com.interviewquestion.databasemanager;

import android.content.Context;

import com.interviewquestion.repository.databasemodel.Android;
import com.interviewquestion.repository.databasemodel.Ios;
import com.interviewquestion.repository.databasemodel.Java;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private DatabaseHelper databaseHelper;

    private DatabaseManager(Context ctx) {
        databaseHelper = new DatabaseHelper(ctx);
    }

    public static DatabaseManager getDataBaseManager(Context ctx) {
        if (instance == null) {
            instance = new DatabaseManager(ctx);
        }
        return instance;
    }

    public DatabaseHelper getDatabaseHelper() {

        return databaseHelper;
    }

    /*
     **********************************ANDROID TABLE FUNCTIONS**********************************
     */

    public void saveQuestionToAndroidTable(List<Android> androidList) {
        try {
            databaseHelper.getAndroidDao().create(androidList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearAndroidTableData() {
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), Android.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Android> fetchAndroidQuestionFromDB() {
        QueryBuilder<Android, Integer> queryBuilder = databaseHelper.getAndroidDao().queryBuilder();
        try {
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
     **********************************IOS TABLE FUNCTIONS**********************************
     */

    public void saveQuestionToIosTable(List<Ios> iosList) {
        try {
            databaseHelper.getIosDao().create(iosList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearIosTableData() {
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), Ios.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ios> fetchIosQuestionFromDB() {
        QueryBuilder<Ios, Integer> queryBuilder = databaseHelper.getIosDao().queryBuilder();
        try {
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
     **********************************JAVA TABLE FUNCTIONS**********************************
     */

    public void saveQuestionToJavaTable(List<Java> iosList) {
        try {
            databaseHelper.getJavaDao().create(iosList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearJavaTableData() {
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), Java.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Java> fetchJavaQuestionFromDB() {
        QueryBuilder<Java, Integer> queryBuilder = databaseHelper.getJavaDao().queryBuilder();
        try {
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
