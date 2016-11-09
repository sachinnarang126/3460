package com.tech.quiz.view.views;

import library.mvp.MvpView;

/**
 * Created by root on 28/9/16.
 */

public interface HomeView extends MvpView {

    void onError(String error);

    void onSuccess();
}
