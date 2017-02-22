package com.tech.quiz.view.views;

import android.support.v7.widget.Toolbar;

import library.mvp.BaseView;

/**
 * @author Sachin Narang
 */

public interface HomeView extends BaseView {

    void onError(String error);

    void onSuccess();

    Toolbar getToolBar();
}
