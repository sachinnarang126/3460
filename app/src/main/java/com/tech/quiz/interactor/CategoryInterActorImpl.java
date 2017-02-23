package com.tech.quiz.interactor;

import android.content.Context;

import com.tech.R;
import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.repositories.interactor.CategoryInterActor;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author Sachin Narang
 */

public class CategoryInterActorImpl implements CategoryInterActor {

    private final Context context;

    public CategoryInterActorImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getJavaQuote() {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(context);
        return "Total Question: " + databaseManager.fetchCountOfAllJavaQuestion() + ", Answered: " + databaseManager.getAnsweredJavaQuestionCount();
    }

    @Override
    public String getAndroidQuote() {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(context);
        return "Total Question: " + databaseManager.fetchCountOfAllAndroidQuestion() + ", Answered: " + databaseManager.getAnsweredAndroidQuestionCount();
    }

    @Override
    public String getIosQuote() {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(context);
        return "Total Question: " + databaseManager.fetchCountOfAllIosQuestion() + ", Answered: " + databaseManager.getAnsweredIosQuestionCount();
    }

    @Override
    public void getJavaQuestions(final OnQuestionResponseListener questionResponseListener, final boolean isShowAnsweredQuestion) {
        DatabaseManager.getDataBaseManager(context).fetchJavaQuestionFromDB(isShowAnsweredQuestion).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<List<Java>>() {
                    @Override
                    public void call(List<Java> javaList) {
                        if (javaList.size() > 0) {
                            questionResponseListener.onSuccess(javaList);
                        } else if (!isShowAnsweredQuestion) {
                            if (DatabaseManager.getDataBaseManager(context).fetchCountOfAllJavaQuestion() > 0)
                                questionResponseListener.onError(context.getString(R.string.load_answered_question), true);
                            else
                                questionResponseListener.onError(context.getString(R.string.error_no_question), false);
                        } else {
                            questionResponseListener.onError(context.getString(R.string.error_no_question), false);
                        }
                    }
                });
    }

    @Override
    public void getAndroidQuestions(final OnQuestionResponseListener questionResponseListener, final boolean isShowAnsweredQuestion) {
        DatabaseManager.getDataBaseManager(context).fetchAndroidQuestionFromDB(isShowAnsweredQuestion).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<List<Android>>() {
                    @Override
                    public void call(List<Android> androidList) {
                        if (androidList.size() > 0) {
                            questionResponseListener.onSuccess(androidList);
                        } else if (!isShowAnsweredQuestion) {
                            if (DatabaseManager.getDataBaseManager(context).fetchCountOfAllAndroidQuestion() > 0)
                                questionResponseListener.onError("No unanswered question found, Do you want to load answered question?", true);
                            else
                                questionResponseListener.onError("No question found..!!!", false);
                        } else {
                            questionResponseListener.onError("No question found..!!!", false);
                        }
                    }
                });
    }

    @Override
    public void getIosQuestion(final OnQuestionResponseListener questionResponseListener, final boolean isShowAnsweredQuestion) {
        DatabaseManager.getDataBaseManager(context).fetchIosQuestionFromDB(isShowAnsweredQuestion).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<List<Ios>>() {
                    @Override
                    public void call(List<Ios> iosList) {
                        if (iosList.size() > 0) {
                            questionResponseListener.onSuccess(iosList);
                        } else if (!isShowAnsweredQuestion) {
                            if (DatabaseManager.getDataBaseManager(context).fetchCountOfAllIosQuestion() > 0)
                                questionResponseListener.onError("No unanswered question found, Do you want to load answered question?", true);
                            else
                                questionResponseListener.onError("No question found..!!!", false);
                        } else {
                            questionResponseListener.onError("No question found..!!!", false);
                        }
                    }
                });
    }
}
