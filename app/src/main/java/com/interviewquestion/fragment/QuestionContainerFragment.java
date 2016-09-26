package com.interviewquestion.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;

import com.interviewquestion.R;
import com.interviewquestion.adapter.QuestionPagerAdapter;
import com.interviewquestion.basecontroller.AppCompatFragment;
import com.interviewquestion.repository.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionContainerFragment extends AppCompatFragment {

    private ProgressBar progressBar;
    private ViewPager viewPager;
    private QuestionPagerAdapter questionPagerAdapter;
    private List<Question> questionList;

    public static QuestionContainerFragment getInstance(int color) {
        QuestionContainerFragment questionContainerFragment = new QuestionContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        questionContainerFragment.setArguments(bundle);
        return questionContainerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        questionList = new ArrayList<>();

        questionPagerAdapter = new QuestionPagerAdapter(getChildFragmentManager(),questionList);
        viewPager.setAdapter(questionPagerAdapter);
//        viewPager.setBackgroundColor();
    }
}
