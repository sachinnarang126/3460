package com.tech.quiz.view.views;

import library.mvp.BaseView;

/**
 * Created by root on 29/9/16.
 */

public interface QuestionView extends BaseView {

    void showProgress();

    void hideProgress();

    String getTechnology();
}
