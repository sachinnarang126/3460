package com.interviewquestion.databasemanager;

import android.content.Context;

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


    /*
     **********************************IOS TABLE FUNCTIONS**********************************
     */


    /*
     **********************************JAVA TABLE FUNCTIONS**********************************
     */


}
