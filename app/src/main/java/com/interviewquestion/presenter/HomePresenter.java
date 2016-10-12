package com.interviewquestion.presenter;

import com.interviewquestion.repository.QuestionResponse;

import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface HomePresenter {

    void onDestroy();

    void prepareToFetchQuestion();

    void saveDataToDB(List<QuestionResponse.Response> questionList, int serviceType);

}
