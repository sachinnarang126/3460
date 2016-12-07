package com.tech.quiz.models.databasemodel;

import com.j256.ormlite.field.DatabaseField;

public class Questions {

    public static final String ID = "question_id";
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

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = ID)
    private int questionId;

    @DatabaseField(columnName = USER_LEVEL)
    private int userLevel;

    @DatabaseField(columnName = CATEGORY, columnDefinition = "text collate nocase")
    private String category;

    @DatabaseField(columnName = QUESTION)
    private String question;

    @DatabaseField(columnName = OPTION_A)
    private String a;

    @DatabaseField(columnName = OPTION_B)
    private String b;

    @DatabaseField(columnName = OPTION_C)
    private String c;

    @DatabaseField(columnName = OPTION_D)
    private String d;

    @DatabaseField(columnName = ANSWER)
    private String answer;

    @DatabaseField(columnName = IS_ATTEMPTED)
    private boolean isAttempted;

    @DatabaseField(columnName = IS_CORRECT_ANSWER_PROVIDED)
    private boolean isCorrectAnswerProvided;

    @DatabaseField(columnName = USER_ANSWER)
    private int userAnswer;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
