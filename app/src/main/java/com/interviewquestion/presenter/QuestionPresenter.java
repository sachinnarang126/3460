package com.interviewquestion.presenter;

import com.interviewquestion.adapter.QuestionPagerAdapter;
import com.interviewquestion.repository.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public interface QuestionPresenter {

    List<Question.Response> shuffledQuestionList = new ArrayList<>();

    void onDestroy();

    void onCreate();

    void prepareListToShowAllQuestion();

    void prepareListToShowUnansweredQuestion();

    void prepareListToShowAnsweredQuestion();

    void prepareListToResetAll();

    void shuffleQuestion();

    QuestionPagerAdapter initAdapter();

}
