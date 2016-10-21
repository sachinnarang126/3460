package com.interviewquestion.models.databasemodel;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sachin on 10/10/16.
 */

@DatabaseTable(tableName = "JAVA")
public class Java extends Questions {

    @Override
    public int getQuestionId() {
        return super.getQuestionId();
    }

    @Override
    public void setQuestionId(int questionId) {
        super.setQuestionId(questionId);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public int getUserLevel() {
        return super.getUserLevel();
    }

    @Override
    public void setUserLevel(int userLevel) {
        super.setUserLevel(userLevel);
    }

    @Override
    public String getCategory() {
        return super.getCategory();
    }

    @Override
    public void setCategory(String category) {
        super.setCategory(category);
    }

    @Override
    public String getQuestion() {
        return super.getQuestion();
    }

    @Override
    public void setQuestion(String question) {
        super.setQuestion(question);
    }

    @Override
    public String getA() {
        return super.getA();
    }

    @Override
    public void setA(String a) {
        super.setA(a);
    }

    @Override
    public String getB() {
        return super.getB();
    }

    @Override
    public void setB(String b) {
        super.setB(b);
    }

    @Override
    public String getC() {
        return super.getC();
    }

    @Override
    public void setC(String c) {
        super.setC(c);
    }

    @Override
    public String getD() {
        return super.getD();
    }

    @Override
    public void setD(String d) {
        super.setD(d);
    }

    @Override
    public String getAnswer() {
        return super.getAnswer();
    }

    @Override
    public void setAnswer(String answer) {
        super.setAnswer(answer);
    }

    @Override
    public boolean isAttempted() {
        return super.isAttempted();
    }

    @Override
    public void setAttempted(boolean attempted) {
        super.setAttempted(attempted);
    }

    @Override
    public boolean isCorrectAnswerProvided() {
        return super.isCorrectAnswerProvided();
    }

    @Override
    public void setCorrectAnswerProvided(boolean correctAnswerProvided) {
        super.setCorrectAnswerProvided(correctAnswerProvided);
    }

    @Override
    public int getUserAnswer() {
        return super.getUserAnswer();
    }

    @Override
    public void setUserAnswer(int userAnswer) {
        super.setUserAnswer(userAnswer);
    }
}
