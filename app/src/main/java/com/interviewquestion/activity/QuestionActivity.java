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
import java.util.Iterator;
import java.util.List;

public class QuestionActivity extends AppBaseCompatActivity {

    private List<Question.Response> shuffledQuestionList;
    private ProgressBar progressBar;
    private QuestionPagerAdapter questionPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("title"));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        shuffledQuestionList = new ArrayList<>();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());
        shuffleQuestion(shuffledQuestionList);
        Collections.shuffle(shuffledQuestionList);

        DataHolder.getInstance().setShuffledQuestionList(shuffledQuestionList);

        questionPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager(), shuffledQuestionList);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_show_answered:
                getSupportActionBar().setSubtitle(getString(R.string.answered));
                prepareListToShowAnsweredQuestion();
                break;

            case R.id.action_show_unanswered:
                getSupportActionBar().setSubtitle(getString(R.string.unanswered));
                prepareListToShowUnansweredQuestion();
                break;

            case R.id.action_show_all:
                getSupportActionBar().setSubtitle(getString(R.string.all));
                prepareListToShowAllQuestion();
                break;

            case R.id.action_reset_all:
                getSupportActionBar().setSubtitle(getString(R.string.reset));
                prepareListToResetAll();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.action_show_all).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    private void prepareListToShowAllQuestion() {
        shuffledQuestionList.clear();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());
        questionPagerAdapter.notifyDataSetChanged();
    }

    private void prepareListToShowUnansweredQuestion() {
        progressBar.setVisibility(View.VISIBLE);
        shuffledQuestionList.clear();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());
        Iterator<Question.Response> iterator = shuffledQuestionList.iterator();
        while (iterator.hasNext()) {
            Question.Response response = iterator.next();
            if (response.isAttempted())
                iterator.remove();
        }
        questionPagerAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    private void prepareListToShowAnsweredQuestion() {
        progressBar.setVisibility(View.VISIBLE);
        shuffledQuestionList.clear();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());
        Iterator<Question.Response> iterator = shuffledQuestionList.iterator();
        while (iterator.hasNext()) {
            Question.Response response = iterator.next();
            if (!response.isAttempted())
                iterator.remove();
        }
        questionPagerAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    private void prepareListToResetAll() {
        progressBar.setVisibility(View.VISIBLE);
        shuffledQuestionList.clear();
        shuffledQuestionList.addAll(DataHolder.getInstance().getQuestionList());

        shuffleQuestion(shuffledQuestionList);

        questionPagerAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
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
