package com.tech.quiz.repositories.presenter;

import android.content.DialogInterface;

import com.tech.quiz.adapter.CategoryAdapter;
import com.tech.quiz.models.databasemodel.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sachin Narang
 */

public interface CategoryPresenter {

    List<String> categoryList = new ArrayList<>();

    void onDestroy();

    void onCreate();

    void onStart();

    void prepareToFetchQuestionFromDB(int serviceType);

    void prepareToFetchQuestionFromDB(int serviceType, boolean isShowAnsweredQuestion);

    void showAnsweredQuestionDialog(String message, DialogInterface.OnClickListener okListener);

    void showQuestions(int position);

    void updateUI(List<Questions> questionList);

    CategoryAdapter initCategoryAdapter();

    void clearCategoryAdapter();

    <T extends Questions> void castToQuestions(List<T> questionListFromDB);
}
