package com.tech.quiz.repositories.presenter;

import org.json.JSONArray;

/**
 * @author Sachin Narang
 */

public interface UpdateQuestionPresenter {
    void parseJson(JSONArray jsonArray, int technology);
}
