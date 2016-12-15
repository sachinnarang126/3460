package com.tech.quiz.view.views;

import library.mvp.BaseView;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryView extends BaseView {
    void showProgress();

    void hideProgress();

    void onError(String error);

    void manageRecyclerView(int visibility);
}
