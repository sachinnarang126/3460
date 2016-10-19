package com.interviewquestion.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.interviewquestion.models.databasemodel.Questions;
import com.interviewquestion.view.fragment.QuestionFragment;

import java.util.List;

/**
 * Created by sachin on 25/09/16.
 */

public class QuestionPagerAdapter extends FragmentStatePagerAdapter {

    //    private int size;
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

    /*@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }*/
}
