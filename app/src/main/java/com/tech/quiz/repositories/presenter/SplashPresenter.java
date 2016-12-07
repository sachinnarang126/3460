package com.tech.quiz.repositories.presenter;

import com.tech.quiz.models.bean.QuestionResponse;

import java.util.List;

public interface SplashPresenter {

    void onDestroy();

    void prepareToFetchQuestion();

    void saveDataToDB(List<QuestionResponse.Response> questionList, int serviceType);

    void goToHomeActivity();

    void queryInventory();

}
