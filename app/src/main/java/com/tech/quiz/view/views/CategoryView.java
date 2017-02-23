package com.tech.quiz.view.views;

import android.widget.TextView;

import library.mvp.BaseView;

/**
 * @author Sachin Narang
 */

public interface CategoryView extends BaseView {

    TextView getQuestionCountView();

    void showProgress();

    void hideProgress();

    void onError(String error);

    void manageRecyclerView(int visibility);

    int getServiceType();

    boolean getUserVisibleHint();
}
