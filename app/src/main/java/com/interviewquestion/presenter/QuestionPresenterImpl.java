package com.interviewquestion.presenter;

import com.interviewquestion.activity.QuestionActivity;
import com.interviewquestion.adapter.QuestionPagerAdapter;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.repository.databasemodel.Questions;
import com.interviewquestion.view.QuestionView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public class QuestionPresenterImpl implements QuestionPresenter {

    private WeakReference<QuestionView> questionView;
    private QuestionPagerAdapter questionPagerAdapter;

    public QuestionPresenterImpl(WeakReference<QuestionView> questionView) {
        this.questionView = questionView;
    }

    @Override
    public void onCreate() {
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());
        shuffleQuestion();
        Collections.shuffle(shuffledQuestionList);
        DataHolder.getInstance().setShuffledQuestionList(shuffledQuestionList);
    }

    @Override
    public void onDestroy() {
        questionView.clear();
        shuffledQuestionList.clear();
        questionPagerAdapter = null;
    }

    @Override
    public void prepareListToShowAllQuestion() {
        questionView.get().showProgress();
        shuffledQuestionList.clear();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());
        questionPagerAdapter.notifyDataSetChanged();
        questionView.get().hideProgress();
    }

    @Override
    public void prepareListToShowUnansweredQuestion() {
        questionView.get().showProgress();
        shuffledQuestionList.clear();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());
        Iterator<Questions> iterator = shuffledQuestionList.iterator();
        while (iterator.hasNext()) {
            Questions response = iterator.next();
            if (response.isAttempted())
                iterator.remove();
        }
        questionPagerAdapter.notifyDataSetChanged();
        questionView.get().hideProgress();
    }

    @Override
    public void prepareListToShowAnsweredQuestion() {
        questionView.get().showProgress();
        shuffledQuestionList.clear();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());
        Iterator<Questions> iterator = shuffledQuestionList.iterator();
        while (iterator.hasNext()) {
            Questions response = iterator.next();
            if (!response.isAttempted())
                iterator.remove();
        }
        questionPagerAdapter.notifyDataSetChanged();
        questionView.get().hideProgress();
    }

    @Override
    public void prepareListToResetAll() {
        questionView.get().showProgress();
        shuffledQuestionList.clear();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());
        shuffleQuestion();
        questionPagerAdapter.notifyDataSetChanged();
        questionView.get().hideProgress();
    }

    @Override
    public void shuffleQuestion() {
        List<String> shuffledOptionList = new ArrayList<>();
        for (Questions response : shuffledQuestionList) {
            shuffledOptionList.clear();
            response.setAttempted(false);
            response.setCorrectAnswerProvided(false);
            response.setUserAnswer(0);
            String answer = "";
            switch (response.getAnswer()) {
                case "1":
                    answer = response.getA();
                    break;

                case "2":
                    answer = response.getB();
                    break;

                case "3":
                    answer = response.getC();
                    break;

                case "4":
                    answer = response.getD();
                    break;
            }

            shuffledOptionList.add(response.getA());
            shuffledOptionList.add(response.getB());
            shuffledOptionList.add(response.getC());
            shuffledOptionList.add(response.getD());

            Collections.shuffle(shuffledOptionList);

            int answerIndex = shuffledOptionList.indexOf(answer);

            response.setAnswer(String.valueOf(answerIndex + 1));

            response.setA(shuffledOptionList.get(0));
            response.setB(shuffledOptionList.get(1));
            response.setC(shuffledOptionList.get(2));
            response.setD(shuffledOptionList.get(3));
        }
    }

    @Override
    public QuestionPagerAdapter initAdapter() {
        return questionPagerAdapter = new QuestionPagerAdapter(((QuestionActivity) questionView.get()).getSupportFragmentManager(),
                shuffledQuestionList);
    }
}
