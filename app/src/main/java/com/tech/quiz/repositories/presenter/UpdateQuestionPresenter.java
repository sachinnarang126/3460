package com.tech.quiz.repositories.presenter;

import org.json.JSONArray;

public interface UpdateQuestionPresenter {
    void parseJson(JSONArray jsonArray, int technology);
}
