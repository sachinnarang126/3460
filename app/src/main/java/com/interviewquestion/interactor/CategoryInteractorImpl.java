package com.interviewquestion.interactor;

import android.content.Context;

/**
 * Created by root on 28/9/16.
 */

public class CategoryInteractorImpl implements CategoryInteractor {

    private Context context;

    public CategoryInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getJavaQuestions(final OnQuestionResponseListener questionResponseListener) {
        // get Java question from db
    }

    @Override
    public void getAndroidQuestions(final OnQuestionResponseListener questionResponseListener) {
        // get Android question from db
    }

    @Override
    public void getIosQuestion(final OnQuestionResponseListener questionResponseListener) {
        // get ios question from db
    }
}
