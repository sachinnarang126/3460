package com.interviewquestion.models.databasemodel;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by sachin on 11/10/16.
 */

public class Questions {

    public static final String ID = "id";
    public static final String CATEGORY = "category";
    public static final String USER_LEVEL = "user_level";
    public static final String QUESTION = "question";
    public static final String OPTION_A = "a";
    public static final String OPTION_B = "b";
    public static final String OPTION_C = "c";
    public static final String OPTION_D = "d";
    public static final String ANSWER = "answer";
    public static final String IS_ATTEMPTED = "is_attempted";
    public static final String IS_CORRECT_ANSWER_PROVIDED = "is_correct_answer_provided";
    public static final String USER_ANSWER = "user_answer";

    @DatabaseField(columnName = ID, generatedId = true)
    protected int id;

    @DatabaseField(columnName = USER_LEVEL)
    protected int userLevel;

    @DatabaseField(columnName = CATEGORY, columnDefinition = "text collate nocase")
    protected String category;

    @DatabaseField(columnName = QUESTION)
    protected String question;

    @DatabaseField(columnName = OPTION_A)
    protected String a;

    @DatabaseField(columnName = OPTION_B)
    protected String b;

    @DatabaseField(columnName = OPTION_C)
    protected String c;

    @DatabaseField(columnName = OPTION_D)
    protected String d;

    @DatabaseField(columnName = ANSWER)
    protected String answer;

    @DatabaseField(columnName = IS_ATTEMPTED)
    protected boolean isAttempted;

    @DatabaseField(columnName = IS_CORRECT_ANSWER_PROVIDED)
    protected boolean isCorrectAnswerProvided;

    @DatabaseField(columnName = USER_ANSWER)
    protected int userAnswer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAttempted() {
        return isAttempted;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }

    public boolean isCorrectAnswerProvided() {
        return isCorrectAnswerProvided;
    }

    public void setCorrectAnswerProvided(boolean correctAnswerProvided) {
        isCorrectAnswerProvided = correctAnswerProvided;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }
}
