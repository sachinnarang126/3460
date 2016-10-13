package com.interviewquestion.repositories.presenter;

import com.interviewquestion.models.QuestionResponse;

import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface HomePresenter {

    void onDestroy();

    void prepareToFetchQuestion();

    void saveDataToDB(List<QuestionResponse.Response> questionList, int serviceType);

}
