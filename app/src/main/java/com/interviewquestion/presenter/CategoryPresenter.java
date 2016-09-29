package com.interviewquestion.presenter;

import com.interviewquestion.adapter.CategoryAdapter;
import com.interviewquestion.repository.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryPresenter {

    List<String> categoryList = new ArrayList<>();

    void onDestroy();

    void prepareToFetchQuestion(int serviceType);

    void showQuestions(int position);

    void displayDataReloadAlert();

    void updateUI(List<Question.Response> responseList);

    CategoryAdapter initCategoryAdapter();
}
