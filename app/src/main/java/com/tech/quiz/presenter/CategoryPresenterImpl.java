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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public class CategoryPresenterImpl implements CategoryPresenter, CategoryInteractor.OnQuestionResponseListener {

    private WeakReference<CategoryView> categoryView;
    private CategoryInteractor categoryInteractor;
    private CategoryAdapter categoryAdapter;
    private List<Questions> questionList;
    private boolean hasToShowRecyclerView;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            categoryView.get().manageRecyclerView(View.VISIBLE);
            categoryView.get().hideProgress();
            hasToShowRecyclerView = true;
        }
    };

    public CategoryPresenterImpl(WeakReference<CategoryView> categoryView) {
        this.categoryView = categoryView;
        categoryInteractor = new CategoryInteractorImpl(((CategoryFragment) categoryView.get()).getContext());
    }

    @Override
    public void onDestroy() {
        hasToShowRecyclerView = false;
        LocalBroadcastManager.getInstance(((CategoryFragment) categoryView.get()).getActivity()).unregisterReceiver(receiver);
        categoryList.clear();
        questionList = null;
        categoryView.clear();
        categoryAdapter = null;
    }

    @Override
    public void onCreate() {
        hasToShowRecyclerView = true;
        LocalBroadcastManager.getInstance(((CategoryFragment) categoryView.get()).getActivity()).registerReceiver(receiver, new IntentFilter(Constant.CATEGORY_RECEIVER));
    }

    @Override
    public void onStart() {
        if (!hasToShowRecyclerView) {
            categoryView.get().showProgress();
            categoryView.get().manageRecyclerView(View.INVISIBLE);
        } else {
            categoryView.get().hideProgress();
            categoryView.get().manageRecyclerView(View.VISIBLE);
        }
    }

    @Override
    public void prepareToFetchQuestionFromDB(int serviceType) {
        boolean isShowAnsweredQuestion = PreferenceManager
                .getDefaultSharedPreferences(((CategoryFragment) categoryView.get()).getActivity()).getBoolean("prefShowAnsweredQuestion", false);
        if (categoryView.get() != null) {
            categoryView.get().showProgress();

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
    public void prepareToFetchQuestionFromDB(int serviceType, boolean isShowAnsweredQuestion) {

        if (categoryView.get() != null) {
            categoryView.get().showProgress();
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
        CategoryFragment context = ((CategoryFragment) categoryView.get());
        Intent intent = new Intent(context.getActivity(), QuestionActivity.class);
        intent.putExtra("title", categoryList.get(position));
        intent.putExtra("technology", ((CategoryFragment) categoryView.get()).getArguments().getInt("serviceType"));
        context.startActivity(intent);
        hasToShowRecyclerView = false;
    }

    @Override
    public <T extends Questions> void onSuccess(List<T> questionListFromDB) {
        if (categoryView.get() != null) {
            updateUI(castToQuestions(questionListFromDB));
            categoryView.get().hideProgress();
        }
    }

    @Override
    public void onError(String error, boolean hasToLoadQuestionFromDb) {
        if (categoryView.get() != null) {
            categoryView.get().hideProgress();
            if (hasToLoadQuestionFromDb) {
                showAnsweredQuestionDialog(error, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prepareToFetchQuestionFromDB(((CategoryFragment) categoryView.get()).getArguments().getInt("serviceType"), true);
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
        return categoryAdapter = new CategoryAdapter(categoryList, (CategoryFragment) categoryView.get());
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
        new AlertDialog.Builder(((CategoryFragment) categoryView.get()).getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .create()
                .show();
    }

}
