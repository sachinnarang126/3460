package com.interviewquestion.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.interviewquestion.R;
import com.interviewquestion.adapter.QuestionPagerAdapter;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.repository.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

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

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        List<Question.Response> shuffledQuestionList = new ArrayList<>(DataHolder.getInstance().getQuestionList());
        shuffleQuestion(shuffledQuestionList);
        Collections.shuffle(shuffledQuestionList);

        DataHolder.getInstance().setShuffledQuestionList(shuffledQuestionList);

        QuestionPagerAdapter questionPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager(), shuffledQuestionList);
        viewPager.setAdapter(questionPagerAdapter);

    }

    private void shuffleQuestion(List<Question.Response> questionList) {
        List<String> shuffledQuestionList = new ArrayList<>();
        for (Question.Response response : questionList) {
            shuffledQuestionList.clear();

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
