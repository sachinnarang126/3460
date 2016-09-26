package com.interviewquestion.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.interviewquestion.fragment.QuestionFragment;
import com.interviewquestion.repository.Question;

import java.util.List;

/**
 * Created by sachin on 25/09/16.
 */

public class QuestionPagerAdapter extends FragmentStatePagerAdapter {

    private List<Question.Response> questionList;
//    private int color;

    public QuestionPagerAdapter(FragmentManager fm, List<Question.Response> questionList/*, int color*/) {
        super(fm);
        this.questionList = questionList;
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment questionFragment = null;
        try {
            questionFragment = QuestionFragment.getInstance(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionFragment;
    }

    @Override
    public int getCount() {
        return questionList.size();
    }
}
