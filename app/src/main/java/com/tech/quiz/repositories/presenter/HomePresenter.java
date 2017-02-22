package com.tech.quiz.repositories.presenter;

import android.support.v4.app.FragmentManager;

import com.tech.quiz.adapter.PagerAdapter;
import com.tech.quiz.models.bean.QuestionResponse;

import java.util.List;

/**
 * @author Sachin Narang
 */

public interface HomePresenter {

    void prepareToFetchQuestion();

    PagerAdapter initAdapter(FragmentManager fm);

    void saveDataToDB(List<QuestionResponse.Response> questionList, int serviceType);

    void saveTimeToPreference();

    boolean hasToFetchQuestionFromServer();

}
