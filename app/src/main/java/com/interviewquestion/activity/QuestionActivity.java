package com.interviewquestion.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.interviewquestion.R;
import com.interviewquestion.adapter.QuestionPagerAdapter;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.repository.Question;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

//    private ProgressBar progressBar;
//    private ViewPager viewPager;
//    private QuestionPagerAdapter questionPagerAdapter;
//    private List<Question.Response> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        List<Question.Response> questionList = DataHolder.getInstance().getQuestionList();

        QuestionPagerAdapter questionPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager(), questionList);
        viewPager.setAdapter(questionPagerAdapter);
    }
}
