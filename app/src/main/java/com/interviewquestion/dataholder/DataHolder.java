package com.interviewquestion.dataholder;

import android.content.Context;
import android.content.SharedPreferences;

import com.interviewquestion.repository.Question;
import com.interviewquestion.util.Constant;

import java.util.List;

public class DataHolder {
    private static DataHolder dataHolder;
    private List<Question.Response> questionList;
    private List<Question.Response> shuffledQuestionList;

    public List<Question.Response> getShuffledQuestionList() {
        return shuffledQuestionList;
    }

    public void setShuffledQuestionList(List<Question.Response> shuffledQuestionList) {
        this.shuffledQuestionList = shuffledQuestionList;
    }

    public List<Question.Response> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question.Response> questionList) {
        this.questionList = questionList;
    }

    public SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constant.SHARED_PREF, 0);
    }

    private DataHolder() {
        //singleton
    }

    public synchronized static DataHolder getInstance() {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
        }
        return dataHolder;
    }

}
