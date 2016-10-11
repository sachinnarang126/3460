package com.interviewquestion.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.interviewquestion.fragment.QuestionFragment;
import com.interviewquestion.repository.databasemodel.Questions;

import java.util.List;

/**
 * Created by sachin on 25/09/16.
 */

public class QuestionPagerAdapter extends FragmentStatePagerAdapter {

    //    private int size;
    private List<Questions> questionList;

    public QuestionPagerAdapter(FragmentManager fm, List<Questions> questionList) {
        super(fm);
        this.questionList = questionList;
//        size = questionList.size();
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment questionFragment = null;
        try {
            questionFragment = QuestionFragment.getInstance(position, questionList.size());
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

    /*@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }*/
}
