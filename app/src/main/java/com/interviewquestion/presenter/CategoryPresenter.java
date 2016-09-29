package com.interviewquestion.presenter;

import com.interviewquestion.repository.Question;

import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryPresenter {

    void onDestroy();

    void prepareToFetchQuestion(int serviceType);

    void showQuestions(int position, String category, List<Question.Response> questionList);

    void displayDataReloadAlert();
}
