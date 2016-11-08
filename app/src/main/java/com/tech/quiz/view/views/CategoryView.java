package com.tech.quiz.view.views;

import library.mvp.MvpView;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryView extends MvpView {
    void showProgress();

    void hideProgress();

    void onError(String error);

    void manageRecyclerView(int visibility);
}
