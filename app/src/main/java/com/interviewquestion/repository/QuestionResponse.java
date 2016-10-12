package com.interviewquestion.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by sachin on 25/09/16.
 */


@Generated("org.jsonschema2pojo")
public class QuestionResponse {

    @SerializedName("response")
    @Expose
    private List<List<Response>> response = new ArrayList<List<Response>>();
    @SerializedName("status")
    @Expose
    private int status;

    /**
     * @return The response
     */
    public List<List<Response>> getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(List<List<Response>> response) {
        this.response = response;
    }

    /**
     * @return The status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(int status) {
        this.status = status;
    }


    @Generated("org.jsonschema2pojo")
    public class Response {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("question_type")
        @Expose
        private String userLevel;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("a")
        @Expose
        private String a;
        @SerializedName("b")
        @Expose
        private String b;
        @SerializedName("c")
        @Expose
        private String c;
        @SerializedName("d")
        @Expose
        private String d;
        @SerializedName("answer")
        @Expose
        private String answer;

        private boolean isAttempted;

        private boolean isCorrectAnswerProvided;

        private int userAnswer;

        private int userChoice;

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

        public int getUserChoice() {
            return userChoice;
        }

        public void setUserChoice(int userChoice) {
            this.userChoice = userChoice;
        }

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The userLevel
         */
        public String getUserLevel() {
            return userLevel;
        }

        /**
         * @param userLevel The userLevel
         */
        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }

        /**
         * @return The category
         */
        public String getCategory() {
            return category;
        }

        /**
         * @param category The category
         */
        public void setCategory(String category) {
            this.category = category;
        }

        /**
         * @return The question
         */
        public String getQuestion() {
            return question;
        }

        /**
         * @param question The question
         */
        public void setQuestion(String question) {
            this.question = question;
        }

        /**
         * @return The a
         */
        public String getA() {
            return a;
        }

        /**
         * @param a The a
         */
        public void setA(String a) {
            this.a = a;
        }

        /**
         * @return The b
         */
        public String getB() {
            return b;
        }

        /**
         * @param b The b
         */
        public void setB(String b) {
            this.b = b;
        }

        /**
         * @return The c
         */
        public String getC() {
            return c;
        }

        /**
         * @param c The c
         */
        public void setC(String c) {
            this.c = c;
        }

        /**
         * @return The d
         */
        public String getD() {
            return d;
        }

        /**
         * @param d The d
         */
        public void setD(String d) {
            this.d = d;
        }

        /**
         * @return The answer
         */
        public String getAnswer() {
            return answer;
        }

        /**
         * @param answer The answer
         */
        public void setAnswer(String answer) {
            this.answer = answer;
        }

    }
}