package com.tech.quiz.interactor;

import android.content.Context;

import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.repositories.interactor.CategoryInteractor;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CategoryInteractorImpl implements CategoryInteractor {

    private Context context;

    public CategoryInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getJavaQuestions(final OnQuestionResponseListener questionResponseListener, final boolean isShowAnsweredQuestion) {
        DatabaseManager.getDataBaseManager(context).fetchJavaQuestionFromDB(isShowAnsweredQuestion).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<List<Java>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Java> javaList) {
                        if (javaList.size() > 0) {
                            questionResponseListener.onSuccess(javaList);
                        } else if (!isShowAnsweredQuestion) {
                            if (DatabaseManager.getDataBaseManager(context).fetchCountOfAllJavaQuestion() > 0)
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
    public void getAndroidQuestions(final OnQuestionResponseListener questionResponseListener, final boolean isShowAnsweredQuestion) {
        DatabaseManager.getDataBaseManager(context).fetchAndroidQuestionFromDB(isShowAnsweredQuestion).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<List<Android>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Android> androidList) {
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
                subscribe(new Observer<List<Ios>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Ios> iosList) {
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
