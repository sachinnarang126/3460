package com.tech.quiz.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.tech.quiz.adapter.CategoryAdapter;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.interactor.CategoryInterActorImpl;
import com.tech.quiz.models.databasemodel.Questions;
import com.tech.quiz.repositories.interactor.CategoryInterActor;
import com.tech.quiz.repositories.presenter.CategoryPresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.QuestionActivity;
import com.tech.quiz.view.fragment.CategoryFragment;
import com.tech.quiz.view.views.CategoryView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import library.mvp.FragmentPresenter;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Sachin Narang
 */

public class CategoryPresenterImpl extends FragmentPresenter<CategoryView, CategoryInterActor> implements CategoryPresenter, CategoryInterActor.OnQuestionResponseListener {

    private CategoryAdapter categoryAdapter;
    private List<Questions> questionList;
    private boolean hasToShowRecyclerView;
    private Map<String, Integer> categoryMap;
    private List<String> categoryList;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isViewAttached()) {
                getView().manageRecyclerView(View.VISIBLE);
                getView().hideProgress();
                hasToShowRecyclerView = true;
            }
        }
    };

    public CategoryPresenterImpl(CategoryView view, Context context) {
        attachView(view, context);
        categoryList = new ArrayList<>();
        categoryMap = new ArrayMap<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasToShowRecyclerView = false;
        if (isViewAttached())
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        categoryList.clear();
        categoryMap.clear();
        questionList = null;
        categoryAdapter = null;
        categoryMap = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasToShowRecyclerView = true;
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(Constant.CATEGORY_RECEIVER));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isViewAttached()) {
            if (!hasToShowRecyclerView) {
                getView().showProgress();
                getView().manageRecyclerView(View.INVISIBLE);
            } else {
                getView().hideProgress();
                getView().manageRecyclerView(View.VISIBLE);
            }
        }
    }

    @Override
    public void prepareToFetchQuestionFromDB(int serviceType) {
        if (isViewAttached()) {
            boolean isShowAnsweredQuestion = PreferenceManager
                    .getDefaultSharedPreferences(getContext()).getBoolean("prefShowAnsweredQuestion", false);
            if (isViewAttached()) {
                getView().showProgress();

                switch (serviceType) {
                    case Constant.ANDROID:
                        getInterActor().getAndroidQuestions(this, isShowAnsweredQuestion);
                        break;

                    case Constant.IOS:
                        getInterActor().getIosQuestion(this, isShowAnsweredQuestion);
                        break;

                    case Constant.JAVA:
                        getInterActor().getJavaQuestions(this, isShowAnsweredQuestion);
                        break;
                }
            }
        }
    }

    @Override
    public void prepareToFetchQuestionFromDB(int serviceType, boolean isShowAnsweredQuestion) {

        if (isViewAttached()) {
            getView().showProgress();
            switch (serviceType) {

                case Constant.ANDROID:
                    getInterActor().getAndroidQuestions(this, isShowAnsweredQuestion);
                    break;

                case Constant.IOS:
                    getInterActor().getIosQuestion(this, isShowAnsweredQuestion);
                    break;

                case Constant.JAVA:
                    getInterActor().getJavaQuestions(this, isShowAnsweredQuestion);
                    break;

            }
        }
    }

    @Override
    public void showQuestions(final int position) {
        showTestModeDialog(position);
    }

    private void filterListToShowQuestion(final int position) {
        if (position == 0) {
            DataHolder.getInstance().setQuestionList(questionList);
            goToQuestionActivity(position, false);
        } else {
            getView().manageRecyclerView(View.INVISIBLE);
            getView().showProgress();
            final List<Questions> tempList = new ArrayList<>();
            Observable.from(questionList).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    filter(new Func1<Questions, Boolean>() {
                        @Override
                        public Boolean call(Questions questions) {
                            return questions.getCategory().equalsIgnoreCase(categoryList.get(position));
                        }
                    }).
                    subscribe(new Observer<Questions>() {
                        @Override
                        public void onCompleted() {
                            DataHolder.getInstance().setQuestionList(tempList);
                            getView().manageRecyclerView(View.VISIBLE);
                            getView().hideProgress();
                            goToQuestionActivity(position, false);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Questions questions) {
                            tempList.add(questions);
                        }
                    });
        }
    }

    @Override
    public <T extends Questions> void onSuccess(List<T> questionListFromDB) {
        if (isViewAttached())
            castToQuestions(questionListFromDB);
    }

    @Override
    public void onError(String error, boolean hasToLoadQuestionFromDb) {
        if (isViewAttached()) {
            getView().hideProgress();
            if (hasToLoadQuestionFromDb) {
                showAnsweredQuestionDialog(error, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prepareToFetchQuestionFromDB(((CategoryFragment) getView()).getArguments().getInt("serviceType"), true);
                    }
                });
            } else {
                showAnsweredQuestionDialog(error, null);
            }
        }
    }

    @Override
    public void updateUI(final List<Questions> responseList) {
        questionList = responseList;
        categoryMap.clear();
        categoryList.clear();
        categoryList.add("All Question");
        categoryMap.put("All Question", 0);

        Observable.from(responseList).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                map(new Func1<Questions, Questions>() {
                    @Override
                    public Questions call(Questions questions) {

                        if (categoryMap.containsKey(questions.getCategory())) {
                            categoryMap.put(questions.getCategory(), categoryMap.get(questions.getCategory()) + 1);
                        } else {
                            categoryList.add(questions.getCategory());
                            categoryMap.put(questions.getCategory(), 1);
                        }
                        return questions;
                    }
                }).
                subscribe(new Observer<Questions>() {
                    @Override
                    public void onCompleted() {
                        categoryMap.put("All Question", responseList.size());
                        categoryAdapter.notifyDataSetChanged();
                        getView().hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                    }

                    @Override
                    public void onNext(Questions questions) {

                    }
                });
    }

    @Override
    public CategoryAdapter initCategoryAdapter() {
        return categoryAdapter = new CategoryAdapter(categoryList, categoryMap, (CategoryFragment) getView());
    }

    @Override
    public <T extends Questions> void castToQuestions(List<T> questionListFromDB) {
        final List<Questions> questionsList = new ArrayList<>();

        Observable.from(questionListFromDB).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                map(new Func1<T, Void>() {
                    @Override
                    public Void call(T t) {
                        questionsList.add(t);
                        return null;
                    }
                }).
                subscribe(new Observer<Void>() {
                    @Override
                    public void onCompleted() {
                        updateUI(questionsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }

    @Override
    public void showAnsweredQuestionDialog(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .create()
                .show();
    }

    private void showTestModeDialog(final int position) {
        new AlertDialog.Builder(getContext())
                .setMessage("Quiz Mode?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetToDefaultValue(position, categoryList.get(position));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        filterListToShowQuestion(position);
                    }
                })
                .setNeutralButton("Cancel", null)
                .create()
                .show();
    }

    private void goToQuestionActivity(int position, boolean isQuizMode) {
        CategoryFragment context = ((CategoryFragment) getView());
        Intent intent = new Intent(context.getActivity(), QuestionActivity.class);
        intent.putExtra("title", categoryList.get(position));
        intent.putExtra("isQuizMode", isQuizMode);
        intent.putExtra("technology", context.getArguments().getInt("serviceType"));
        context.startActivity(intent);
        hasToShowRecyclerView = false;
    }

    private void resetToDefaultValue(final int position, final String category) {
        final List<Questions> tempList = new ArrayList<>();
        getView().manageRecyclerView(View.INVISIBLE);
        getView().showProgress();
        Observable.from(questionList).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                filter(new Func1<Questions, Boolean>() {
                    @Override
                    public Boolean call(Questions questions) {
                        return category.equalsIgnoreCase("All Question") ||
                                category.equalsIgnoreCase(questions.getCategory());
                    }
                }).
                map(new Func1<Questions, Questions>() {
                    @Override
                    public Questions call(Questions questions) {
                        questions.setAttempted(false);
                        questions.setUserAnswer(0);
                        questions.setCorrectAnswerProvided(false);
                        return questions;
                    }
                }).
                subscribe(new Observer<Questions>() {
                    @Override
                    public void onCompleted() {
                        DataHolder.getInstance().setQuestionList(tempList);
                        getView().manageRecyclerView(View.VISIBLE);
                        getView().hideProgress();
                        goToQuestionActivity(position, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                    }

                    @Override
                    public void onNext(Questions questions) {
                        tempList.add(questions);
                    }
                });
    }

    @Override
    protected CategoryInterActor createInterActor() {
        return new CategoryInterActorImpl(getContext());
    }
}
