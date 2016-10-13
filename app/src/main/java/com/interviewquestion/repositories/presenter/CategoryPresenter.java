package com.interviewquestion.repositories.presenter;

import com.interviewquestion.adapter.CategoryAdapter;
import com.interviewquestion.models.databasemodel.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryPresenter {

    List<String> categoryList = new ArrayList<>();

    void onDestroy();

    void prepareToFetchQuestionFromDB(int serviceType);

    void showQuestions(int position);

    void updateUI(List<Questions> questionList);

    CategoryAdapter initCategoryAdapter();

    void clearCategoryAdapter();

    <T extends Questions> List<Questions> castToQuestions(List<T> questionListFromDB);
}
