package com.tech.quiz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tech.quiz.models.databasemodel.Questions;
import com.tech.quiz.view.fragment.QuizFragment;

import java.util.List;

/**
 * @author Sachin Narang
 */

public class QuizPagerAdapter extends BaseAdapter {

    private List<Questions> questionList;

    public QuizPagerAdapter(FragmentManager fm, List<Questions> questionList) {
        super(fm);
        this.questionList = questionList;
    }

    @Override
    public Fragment getItem(int position) {
        QuizFragment quizFragment = null;
        try {
            quizFragment = QuizFragment.getInstance(position, questionList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizFragment;
    }

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
