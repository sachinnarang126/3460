package com.interviewquestion.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.interviewquestion.repository.Question;

import java.util.List;

/**
 * Created by sachin on 25/09/16.
 */

public class QuestionPagerAdapter extends FragmentStatePagerAdapter {

    private List<Question> questionList;
//    private int color;

    public QuestionPagerAdapter(FragmentManager fm, List<Question> questionList/*, int color*/) {
        super(fm);
        this.questionList = questionList;
    }

    @Override
    public Fragment getItem(int position) {

        try{

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return /*questionList.size()*/ 5;
    }
}
