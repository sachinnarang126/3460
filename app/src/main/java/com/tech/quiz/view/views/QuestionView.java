package com.tech.quiz.view.views;

import library.mvp.BaseView;

/**
 * @author Sachin Narang
 */

public interface QuestionView extends BaseView {

    void showProgress();

    void hideProgress();

    String getTechnology();
}
