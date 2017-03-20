package com.tech.quiz.view.views;

import library.mvp.BaseView;

/**
 * @author Sachin Narang
 */

public interface DiscussionView extends BaseView {

    void showProgressBar();

    void hideProgressBar();

    void onError(String error);
}
