package com.tech.quiz.view.views;

import library.mvp.BaseView;

/**
 * @author Sachin Narang
 */

public interface SplashView extends BaseView {
    void showProgress();

    void hideProgress();

    void onError(String error);
}
