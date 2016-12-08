package com.tech.quiz.databasemanager;

import android.content.Context;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.TableUtils;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.models.databasemodel.Questions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

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

    /*public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }*/

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

    public Observable<List<Android>> fetchAndroidQuestionFromDB(boolean isShowAnsweredQuestion) {
        QueryBuilder<Android, Integer> queryBuilder = databaseHelper.getAndroidDao().queryBuilder();
        try {
            if (isShowAnsweredQuestion) {
                return Observable.just(queryBuilder.query());
            } else {
                return Observable.just(queryBuilder.where().eq(Questions.IS_ATTEMPTED, false).query());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initDefaultValueToAndroidQuestion() {
        UpdateBuilder<Android, Integer> updateBuilder = databaseHelper.getAndroidDao().updateBuilder();

        try {
            updateBuilder.updateColumnValue(Questions.IS_ATTEMPTED, false).
                    updateColumnValue(Questions.IS_CORRECT_ANSWER_PROVIDED, false).
                    updateColumnValue(Questions.USER_ANSWER, 0).update();
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

    public List<Integer> getAndroidIdList() {
        List<Integer> integerList = new ArrayList<>();
        QueryBuilder<Android, Integer> queryBuilder = databaseHelper.getAndroidDao().queryBuilder();
        try {
            List<Android> androidList = queryBuilder.selectColumns(Questions.ID).query();
            for (Android android : androidList) {
                integerList.add(android.getQuestionId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return integerList;
    }

    public long fetchCountOfAllAndroidQuestion() {
        try {
            return databaseHelper.getAndroidDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateAndroidQuestion(int questionID, String userLevel, String category, String question,
                                      String optionA, String optionB, String optionC, String optionD, int answer) {
        UpdateBuilder<Android, Integer> updateBuilder = databaseHelper.getAndroidDao().updateBuilder();
        try {
            updateBuilder.updateColumnValue(Questions.USER_LEVEL, userLevel).
                    updateColumnValue(Questions.CATEGORY, category).
                    updateColumnValue(Questions.QUESTION, question).
                    updateColumnValue(Questions.OPTION_A, optionA).
                    updateColumnValue(Questions.OPTION_B, optionB).
                    updateColumnValue(Questions.OPTION_C, optionC).
                    updateColumnValue(Questions.OPTION_D, optionD).
                    updateColumnValue(Questions.ANSWER, answer).
                    updateColumnValue(Questions.IS_ATTEMPTED, false).
                    updateColumnValue(Questions.IS_CORRECT_ANSWER_PROVIDED, false).
                    updateColumnValue(Questions.USER_ANSWER, 0);
            updateBuilder.where().eq(Questions.ID, questionID);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getUnansweredAndroidQuestionCount() {
        try {
            return databaseHelper.getAndroidDao().queryBuilder().where().eq(Questions.IS_ATTEMPTED, false).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public long getAnsweredAndroidQuestionCount() {
        try {
            return databaseHelper.getAndroidDao().queryBuilder().where().eq(Questions.IS_ATTEMPTED, true).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    public Observable<List<Ios>> fetchIosQuestionFromDB(boolean isShowAnsweredQuestion) {
        QueryBuilder<Ios, Integer> queryBuilder = databaseHelper.getIosDao().queryBuilder();
        try {
            if (isShowAnsweredQuestion) {
                return Observable.just(queryBuilder.query());
            } else {
                return Observable.just(queryBuilder.where().eq(Questions.IS_ATTEMPTED, false).query());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initDefaultValueToIosQuestion() {
        UpdateBuilder<Ios, Integer> updateBuilder = databaseHelper.getIosDao().updateBuilder();

        try {
            updateBuilder.updateColumnValue(Questions.IS_ATTEMPTED, false).
                    updateColumnValue(Questions.IS_CORRECT_ANSWER_PROVIDED, false).
                    updateColumnValue(Questions.USER_ANSWER, 0).update();
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

    public List<Integer> getIosIdList() {
        List<Integer> integerList = new ArrayList<>();
        QueryBuilder<Ios, Integer> queryBuilder = databaseHelper.getIosDao().queryBuilder();
        try {
            List<Ios> iosIdList = queryBuilder.selectColumns(Questions.ID).query();
            for (Ios ios : iosIdList) {
                integerList.add(ios.getQuestionId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return integerList;
    }

    public long fetchCountOfAllIosQuestion() {
        try {
            return databaseHelper.getIosDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateIosQuestion(int questionID, String userLevel, String category, String question,
                                  String optionA, String optionB, String optionC, String optionD, int answer) {
        UpdateBuilder<Ios, Integer> updateBuilder = databaseHelper.getIosDao().updateBuilder();
        try {
            updateBuilder.updateColumnValue(Questions.USER_LEVEL, userLevel).
                    updateColumnValue(Questions.CATEGORY, category).
                    updateColumnValue(Questions.QUESTION, question).
                    updateColumnValue(Questions.OPTION_A, optionA).
                    updateColumnValue(Questions.OPTION_B, optionB).
                    updateColumnValue(Questions.OPTION_C, optionC).
                    updateColumnValue(Questions.OPTION_D, optionD).
                    updateColumnValue(Questions.ANSWER, answer).
                    updateColumnValue(Questions.IS_ATTEMPTED, false).
                    updateColumnValue(Questions.IS_CORRECT_ANSWER_PROVIDED, false).
                    updateColumnValue(Questions.USER_ANSWER, 0);
            updateBuilder.where().eq(Questions.ID, questionID);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getUnansweredIosQuestionCount() {
        try {
            return databaseHelper.getIosDao().queryBuilder().where().eq(Questions.IS_ATTEMPTED, false).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long getAnsweredIosQuestionCount() {
        try {
            return databaseHelper.getIosDao().queryBuilder().where().eq(Questions.IS_ATTEMPTED, true).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    public Observable<List<Java>> fetchJavaQuestionFromDB(boolean isShowAnsweredQuestion) {
        QueryBuilder<Java, Integer> queryBuilder = databaseHelper.getJavaDao().queryBuilder();
        try {
            if (isShowAnsweredQuestion)
                return Observable.just(queryBuilder.query());
            else
                return Observable.just(queryBuilder.where().eq(Questions.IS_ATTEMPTED, false).query());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initDefaultValueToJavaQuestion() {
        UpdateBuilder<Java, Integer> updateBuilder = databaseHelper.getJavaDao().updateBuilder();

        try {
            updateBuilder.updateColumnValue(Questions.IS_ATTEMPTED, false).
                    updateColumnValue(Questions.IS_CORRECT_ANSWER_PROVIDED, false).
                    updateColumnValue(Questions.USER_ANSWER, 0).update();
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

    public List<Integer> getJavaIdList() {
        List<Integer> integerList = new ArrayList<>();
        QueryBuilder<Java, Integer> queryBuilder = databaseHelper.getJavaDao().queryBuilder();
        try {
            List<Java> javaIdList = queryBuilder.selectColumns(Questions.ID).query();
            for (Java java : javaIdList) {
                integerList.add(java.getQuestionId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return integerList;
    }

    public long fetchCountOfAllJavaQuestion() {
        try {
            return databaseHelper.getJavaDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateJavaQuestion(int questionID, String userLevel, String category, String question,
                                   String optionA, String optionB, String optionC, String optionD, int answer) {
        UpdateBuilder<Java, Integer> updateBuilder = databaseHelper.getJavaDao().updateBuilder();
        try {
            updateBuilder.updateColumnValue(Questions.USER_LEVEL, userLevel).
                    updateColumnValue(Questions.CATEGORY, category).
                    updateColumnValue(Questions.QUESTION, question).
                    updateColumnValue(Questions.OPTION_A, optionA).
                    updateColumnValue(Questions.OPTION_B, optionB).
                    updateColumnValue(Questions.OPTION_C, optionC).
                    updateColumnValue(Questions.OPTION_D, optionD).
                    updateColumnValue(Questions.ANSWER, answer).
                    updateColumnValue(Questions.IS_ATTEMPTED, false).
                    updateColumnValue(Questions.IS_CORRECT_ANSWER_PROVIDED, false).
                    updateColumnValue(Questions.USER_ANSWER, 0);
            updateBuilder.where().eq(Questions.ID, questionID);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getUnansweredJavaQuestionCount() {
        try {
            return databaseHelper.getJavaDao().queryBuilder().where().eq(Questions.IS_ATTEMPTED, false).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long getAnsweredJavaQuestionCount() {
        try {
            return databaseHelper.getJavaDao().queryBuilder().where().eq(Questions.IS_ATTEMPTED, true).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
