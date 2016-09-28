package com.interviewquestion.presenter;

/**
 * Created by root on 28/9/16.
 */

public interface QuestionPresenter {

    void onDestroy();

    void prepareToFetchQuestion(int serviceType);
}
