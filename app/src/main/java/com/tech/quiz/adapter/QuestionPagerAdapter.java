package com.tech.quiz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tech.quiz.models.databasemodel.Questions;
import com.tech.quiz.view.fragment.QuestionFragment;

import java.util.List;

/**
 * @author Sachin Narang
 */

public class QuestionPagerAdapter extends BaseAdapter {

    private List<Questions> questionList;
    private int technology;

    public QuestionPagerAdapter(FragmentManager fm, List<Questions> questionList, int technology) {
        super(fm);
        this.questionList = questionList;
        this.technology = technology;
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment questionFragment = null;
        try {
            questionFragment = QuestionFragment.getInstance(position, questionList.size(), technology);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionFragment;
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
