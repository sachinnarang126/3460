package com.tech.quiz.view.views;

import android.support.v7.widget.Toolbar;
import android.view.Menu;

import library.mvp.BaseView;

/**
 * @author Sachin Narang
 */

public interface HomeView extends BaseView {

    void onError(String error);

    Menu getOptionMenu();

    void onSuccess();

    Toolbar getToolBar();
}
