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

    private int size;

    public QuestionPagerAdapter(FragmentManager fm, List<Question.Response> questionList) {
        super(fm);
        size = questionList.size();
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment questionFragment = null;
        try {
            questionFragment = QuestionFragment.getInstance(position, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionFragment;
    }

    @Override
    public int getCount() {
        return size;
    }
}
