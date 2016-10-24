package com.tech.quiz.repositories.presenter;

import com.tech.quiz.adapter.QuestionPagerAdapter;
import com.tech.quiz.models.databasemodel.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface QuestionPresenter {

    List<Questions> shuffledQuestionList = new ArrayList<>();

    void onDestroy();

    void onCreate();

    void prepareListToShowAllQuestion();

    void prepareListToShowUnansweredQuestion();

    void prepareListToShowAnsweredQuestion();

    void prepareListToResetAll();

    void shuffleQuestion();

    void shuffleQuestionAndResetAllLocally();

    QuestionPagerAdapter initAdapter(int technology);

}
