package com.tech.quiz.repositories.presenter;

import com.tech.quiz.adapter.BaseAdapter;
import com.tech.quiz.models.databasemodel.Questions;

import java.util.ArrayList;
import java.util.List;

public interface QuestionPresenter {

    List<Questions> shuffledQuestionList = new ArrayList<>();

    void onDestroy();

    void onCreate();

    void prepareListToShowAllQuestion();

    void prepareListToShowUnansweredQuestion();

    void prepareListToShowAnsweredQuestion();

    void prepareListToResetAll();

    void shuffleQuestion();

//    void shuffleQuestionAndResetAllLocally();

    void showResult();

    BaseAdapter initAdapter(int technology);

    BaseAdapter initAdapter();

    void checkForQuizCompletion();

}
