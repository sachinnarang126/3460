package com.tech.quiz.view.views;

/**
 * Created by root on 28/9/16.
 */

public interface CategoryView {
    void showProgress();

    void hideProgress();

    void onError(String error);

    void manageRecyclerView(int visibility);
}
