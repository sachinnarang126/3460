package com.tech.quiz.interactor;

import android.content.Context;

import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.repositories.interactor.CategoryInteractor;

import java.util.List;

public class CategoryInteractorImpl implements CategoryInteractor {

    private Context context;

    public CategoryInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getJavaQuestions(final OnQuestionResponseListener questionResponseListener, boolean isShowAnsweredQuestion) {
        List<Java> javaQuestionList = DatabaseManager.getDataBaseManager(context).fetchJavaQuestionFromDB(isShowAnsweredQuestion);
        if (javaQuestionList.size() > 0) {
            questionResponseListener.onSuccess(javaQuestionList);
        } else if (!isShowAnsweredQuestion) {
            if (DatabaseManager.getDataBaseManager(context).fetchCountOfAllJavaQuestion() > 0)
                questionResponseListener.onError("No unanswered question found, Do you want to load answered question?", true);
            else
                questionResponseListener.onError("No question found..!!!", false);
        } else {
            questionResponseListener.onError("No question found..!!!", false);
        }
    }

    @Override
    public void getAndroidQuestions(final OnQuestionResponseListener questionResponseListener, boolean isShowAnsweredQuestion) {

        List<Android> androidQuestionList = DatabaseManager.getDataBaseManager(context).fetchAndroidQuestionFromDB(isShowAnsweredQuestion);
        if (androidQuestionList.size() > 0) {
            questionResponseListener.onSuccess(androidQuestionList);
        } else if (!isShowAnsweredQuestion) {
            if (DatabaseManager.getDataBaseManager(context).fetchCountOfAllAndroidQuestion() > 0)
                questionResponseListener.onError("No unanswered question found, Do you want to load answered question?", true);
            else
                questionResponseListener.onError("No question found..!!!", false);
        } else {
            questionResponseListener.onError("No question found..!!!", false);
        }
    }

    @Override
    public void getIosQuestion(final OnQuestionResponseListener questionResponseListener, boolean isShowAnsweredQuestion) {
        List<Ios> iosQuestionList = DatabaseManager.getDataBaseManager(context).fetchIosQuestionFromDB(isShowAnsweredQuestion);
        if (iosQuestionList.size() > 0) {
            questionResponseListener.onSuccess(iosQuestionList);
        } else if (!isShowAnsweredQuestion) {
            if (DatabaseManager.getDataBaseManager(context).fetchCountOfAllIosQuestion() > 0)
                questionResponseListener.onError("No unanswered question found, Do you want to load answered question?", true);
            else
                questionResponseListener.onError("No question found..!!!", false);
        } else {
            questionResponseListener.onError("No question found..!!!", false);
        }
    }
}
