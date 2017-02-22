package com.tech.quiz.repositories.presenter;

import com.tech.quiz.adapter.BaseAdapter;
import com.tech.quiz.models.databasemodel.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sachin Narang
 */

public interface QuestionPresenter {

    List<Questions> shuffledQuestionList = new ArrayList<>();

    void prepareListToShowAllQuestion();

    void prepareListToShowUnansweredQuestion();

    void prepareListToShowAnsweredQuestion();

    void prepareListToResetAll();

    void shuffleQuestion();

    void showResult();

    BaseAdapter initAdapter(int technology);

    BaseAdapter initAdapter();

    void checkForQuizCompletion();

}
