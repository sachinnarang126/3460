package com.interviewquestion.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.interviewquestion.R;
import com.interviewquestion.adapter.QuestionPagerAdapter;
import com.interviewquestion.basecontroller.AppBaseCompatActivity;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.repository.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppBaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("title"));

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        List<Question.Response> shuffledQuestionList = new ArrayList<>();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());

        shuffleQuestion(shuffledQuestionList);
        Collections.shuffle(shuffledQuestionList);

        DataHolder.getInstance().setShuffledQuestionList(shuffledQuestionList);

        QuestionPagerAdapter questionPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager(), shuffledQuestionList);
        viewPager.setAdapter(questionPagerAdapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_show_answered:

                break;

            case R.id.action_show_unanswered:

                break;

            case R.id.action_show_all:

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void shuffleQuestion(List<Question.Response> questionList) {
        List<String> shuffledQuestionList = new ArrayList<>();
        for (Question.Response response : questionList) {
            shuffledQuestionList.clear();
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

            shuffledQuestionList.add(response.getA());
            shuffledQuestionList.add(response.getB());
            shuffledQuestionList.add(response.getC());
            shuffledQuestionList.add(response.getD());

            Collections.shuffle(shuffledQuestionList);

            int answerIndex = shuffledQuestionList.indexOf(answer);

            response.setAnswer(String.valueOf(answerIndex + 1));

            response.setA(shuffledQuestionList.get(0));
            response.setB(shuffledQuestionList.get(1));
            response.setC(shuffledQuestionList.get(2));
            response.setD(shuffledQuestionList.get(3));
        }
    }

}
