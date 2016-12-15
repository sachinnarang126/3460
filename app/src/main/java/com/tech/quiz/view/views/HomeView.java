package com.tech.quiz.view.views;

import library.mvp.BaseView;

/**
 * Created by root on 28/9/16.
 */

public interface HomeView extends BaseView {

    void onError(String error);

    void onSuccess();
}
