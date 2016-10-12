package com.interviewquestion.interactor;

import android.content.Context;
import android.preference.PreferenceManager;

import com.interviewquestion.databasemanager.DatabaseManager;
import com.interviewquestion.repository.databasemodel.Android;
import com.interviewquestion.repository.databasemodel.Ios;
import com.interviewquestion.repository.databasemodel.Java;

import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public class CategoryInteractorImpl implements CategoryInteractor {

    private Context context;

    public CategoryInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getJavaQuestions(final OnQuestionResponseListener questionResponseListener) {
        // get Java question from db
        boolean isShowAnsweredQuestion = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean("prefShowAnsweredQuestion", false);
        List<Java> javaQuestionList = DatabaseManager.getDataBaseManager(context).fetchJavaQuestionFromDB(isShowAnsweredQuestion);
        if (javaQuestionList.size() > 0) {
            questionResponseListener.onSuccess(javaQuestionList);
        } else {
            questionResponseListener.onError("No Question found");
        }
    }

    @Override
    public void getAndroidQuestions(final OnQuestionResponseListener questionResponseListener) {
        boolean isShowAnsweredQuestion = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean("prefShowAnsweredQuestion", false);
        List<Android> androidQuestionList = DatabaseManager.getDataBaseManager(context).fetchAndroidQuestionFromDB(isShowAnsweredQuestion);
        if (androidQuestionList.size() > 0) {
            questionResponseListener.onSuccess(androidQuestionList);
        } else {
            questionResponseListener.onError("No Question found");
        }
    }

    @Override
    public void getIosQuestion(final OnQuestionResponseListener questionResponseListener) {
        boolean isShowAnsweredQuestion = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean("prefShowAnsweredQuestion", false);
        List<Ios> iosQuestionList = DatabaseManager.getDataBaseManager(context).fetchIosQuestionFromDB(isShowAnsweredQuestion);
        if (iosQuestionList.size() > 0) {
            questionResponseListener.onSuccess(iosQuestionList);
        } else {
            questionResponseListener.onError("No Question found");
        }
    }
}
