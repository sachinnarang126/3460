package com.tech.quiz.view.views;

import library.mvp.BaseView;

/**
 * @author Sachin Narang
 */

public interface CategoryView extends BaseView {
    void showProgress();

    void hideProgress();

    void onError(String error);

    void manageRecyclerView(int visibility);
}
