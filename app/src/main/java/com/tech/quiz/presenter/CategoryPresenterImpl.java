package com.tech.quiz.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.tech.quiz.adapter.CategoryAdapter;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.interactor.CategoryInteractorImpl;
import com.tech.quiz.models.databasemodel.Questions;
import com.tech.quiz.repositories.interactor.CategoryInteractor;
import com.tech.quiz.repositories.presenter.CategoryPresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.QuestionActivity;
import com.tech.quiz.view.fragment.CategoryFragment;
import com.tech.quiz.view.views.CategoryView;

import java.util.ArrayList;
import java.util.List;

import library.mvp.MvpBasePresenter;

public class CategoryPresenterImpl extends MvpBasePresenter<CategoryView> implements CategoryPresenter, CategoryInteractor.OnQuestionResponseListener {

    private CategoryInteractor categoryInteractor;
    private CategoryAdapter categoryAdapter;
    private List<Questions> questionList;
    private boolean hasToShowRecyclerView;

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
        categoryInteractor = new CategoryInteractorImpl(context);
    }

    @Override
    public void onDestroy() {
        hasToShowRecyclerView = false;
        if (isViewAttached())
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        categoryList.clear();
        questionList = null;
        categoryAdapter = null;
        detachView();
    }

    @Override
    public void onCreate() {
        hasToShowRecyclerView = true;
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(Constant.CATEGORY_RECEIVER));
    }

    @Override
    public void onStart() {
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
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

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
                        categoryInteractor.getAndroidQuestions(this, isShowAnsweredQuestion);
                        break;

                    case Constant.IOS:
                        categoryInteractor.getIosQuestion(this, isShowAnsweredQuestion);
                        break;

                    case Constant.JAVA:
                        categoryInteractor.getJavaQuestions(this, isShowAnsweredQuestion);
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

                    categoryInteractor.getAndroidQuestions(this, isShowAnsweredQuestion);
                    break;

                case Constant.IOS:

                    categoryInteractor.getIosQuestion(this, isShowAnsweredQuestion);
                    break;

                case Constant.JAVA:

                    categoryInteractor.getJavaQuestions(this, isShowAnsweredQuestion);
                    break;
            }
        }
    }

    @Override
    public void showQuestions(int position) {
        if (position == 0) {
            DataHolder.getInstance().setQuestionList(questionList);
        } else {
            List<Questions> tempList = new ArrayList<>();
            for (Questions questions : questionList) {
                if (questions.getCategory().equalsIgnoreCase(categoryList.get(position))) {
                    tempList.add(questions);
                }
            }
            DataHolder.getInstance().setQuestionList(tempList);
        }
        CategoryFragment context = ((CategoryFragment) getView());
        Intent intent = new Intent(context.getActivity(), QuestionActivity.class);
        intent.putExtra("title", categoryList.get(position));
        intent.putExtra("technology", context.getArguments().getInt("serviceType"));
        context.startActivity(intent);
        hasToShowRecyclerView = false;
    }

    @Override
    public <T extends Questions> void onSuccess(List<T> questionListFromDB) {
        if (isViewAttached()) {
            updateUI(castToQuestions(questionListFromDB));
            getView().hideProgress();
        }
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
    public void updateUI(List<Questions> responseList) {
        questionList = responseList;
        categoryList.clear();
        categoryList.add("All Question");
        for (Questions questions : responseList) {
            if (!categoryList.contains(questions.getCategory()))
                categoryList.add(questions.getCategory());
        }

        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public CategoryAdapter initCategoryAdapter() {
        return categoryAdapter = new CategoryAdapter(categoryList, (CategoryFragment) getView());
    }

    @Override
    public void clearCategoryAdapter() {
        categoryList.clear();
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public <T extends Questions> List<Questions> castToQuestions(List<T> questionListFromDB) {
        List<Questions> questionsList = new ArrayList<>();
        for (T t : questionListFromDB) {
            questionsList.add(t);
        }
        return questionsList;
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
}
