package com.tech.quiz.repositories.presenter;

import com.tech.quiz.models.bean.QuestionResponse;

import java.util.List;

/**
 * @author Sachin Narang
 */

public interface SplashPresenter {

    void prepareToFetchQuestion();

    void saveDataToDB(List<QuestionResponse.Response> questionList, int serviceType);

    void goToHomeActivity();

    void queryInventory();

}
