package com.interviewquestion.databasemanager;

import android.content.Context;

import com.interviewquestion.repository.databasemodel.Android;
import com.interviewquestion.repository.databasemodel.Ios;
import com.interviewquestion.repository.databasemodel.Java;
import com.interviewquestion.repository.databasemodel.Questions;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
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
     **********************************COMMON FUNCTIONS**********************************
     */

    public void initDefaultValueToAllQuestion() {
        initDefaultValueToAndroidQuestion();
        initDefaultValueToIosQuestion();
        initDefaultValueToJavaQuestion();
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

    public List<Android> fetchAndroidQuestionFromDB( boolean isShowAnsweredQuestion) {
        QueryBuilder<Android, Integer> queryBuilder = databaseHelper.getAndroidDao().queryBuilder();
        try {
            if (isShowAnsweredQuestion) {
                return queryBuilder.query();
            } else {
                return queryBuilder.where().eq(Questions.IS_ATTEMPTED, false).query();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initDefaultValueToAndroidQuestion() {
        UpdateBuilder<Android, Integer> updateBuilder = databaseHelper.getAndroidDao().updateBuilder();

        try {
            updateBuilder.updateColumnValue(Questions.IS_ATTEMPTED, false).
                    updateColumnValue(Questions.IS_CORRECT_ANSWER_PROVIDED, false).
                    updateColumnValue(Questions.USER_ANSWER, 0).
                    updateColumnValue(Questions.USER_CHOICE, 0).update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAndroidQuestion(Android android) {
        try {
            databaseHelper.getAndroidDao().update(android);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List<Ios> fetchIosQuestionFromDB(boolean isShowAnsweredQuestion) {
        QueryBuilder<Ios, Integer> queryBuilder = databaseHelper.getIosDao().queryBuilder();
        try {
            if (isShowAnsweredQuestion) {
                return queryBuilder.query();
            } else {
                return queryBuilder.where().eq(Questions.IS_ATTEMPTED, false).query();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initDefaultValueToIosQuestion() {
        UpdateBuilder<Ios, Integer> updateBuilder = databaseHelper.getIosDao().updateBuilder();

        try {
            updateBuilder.updateColumnValue(Questions.IS_ATTEMPTED, false).
                    updateColumnValue(Questions.IS_CORRECT_ANSWER_PROVIDED, false).
                    updateColumnValue(Questions.USER_ANSWER, 0).
                    updateColumnValue(Questions.USER_CHOICE, 0).update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIosQuestion(Ios ios) {
        try {
            databaseHelper.getIosDao().update(ios);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List<Java> fetchJavaQuestionFromDB(boolean isShowAnsweredQuestion) {
        QueryBuilder<Java, Integer> queryBuilder = databaseHelper.getJavaDao().queryBuilder();
        try {
            if (isShowAnsweredQuestion) {
                return queryBuilder.query();
            } else {
                return queryBuilder.where().eq(Questions.IS_ATTEMPTED, false).query();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initDefaultValueToJavaQuestion() {
        UpdateBuilder<Java, Integer> updateBuilder = databaseHelper.getJavaDao().updateBuilder();

        try {
            updateBuilder.updateColumnValue(Questions.IS_ATTEMPTED, false).
                    updateColumnValue(Questions.IS_CORRECT_ANSWER_PROVIDED, false).
                    updateColumnValue(Questions.USER_ANSWER, 0).
                    updateColumnValue(Questions.USER_CHOICE, 0).update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateJavaQuestion(Java java) {
        try {
            databaseHelper.getJavaDao().update(java);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
