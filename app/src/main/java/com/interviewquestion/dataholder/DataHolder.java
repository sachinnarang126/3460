package com.interviewquestion.dataholder;

import com.interviewquestion.repository.Question;

import java.util.List;

public class DataHolder {
    private static DataHolder dataHolder;
    private List<Question.Response> questionList;

    public List<Question.Response> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question.Response> questionList) {
        this.questionList = questionList;
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
