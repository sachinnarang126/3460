package com.interviewquestion.view;

import com.interviewquestion.repository.Question;

import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryView {
    void showProgress();

    void hideProgress();

    void onError(String error);

    void onSuccess(List<Question.Response> questionList);
}
