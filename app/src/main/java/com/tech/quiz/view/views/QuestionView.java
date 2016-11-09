package com.tech.quiz.view.views;

import library.mvp.MvpView;

/**
 * Created by root on 29/9/16.
 */

public interface QuestionView extends MvpView {

    void showProgress();

    void hideProgress();
}
