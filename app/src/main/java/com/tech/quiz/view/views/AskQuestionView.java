package com.tech.quiz.view.views;

import library.mvp.BaseView;

/**
 * @author Sachin Narang
 */

public interface AskQuestionView extends BaseView {
    void onError(String error);

    void resetToDefaultValue();
}
