package com.tech.quiz.repositories.presenter;

import org.json.JSONArray;

/**
 * Created by root on 19/10/16.
 */

public interface UpdateQuestionPresenter {
    void parseJson(JSONArray jsonArray, int technology);
}
