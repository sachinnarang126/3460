package com.interviewquestion.presenter;

import com.interviewquestion.repository.Question;

import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface SplashPresenter {

    void onDestroy();

    void prepareToFetchQuestion();

    void displayDataReloadAlert();

    void saveDataToDB(List<Question.Response> questionList,int serviceType);

    void goToHomeActivity();

}
