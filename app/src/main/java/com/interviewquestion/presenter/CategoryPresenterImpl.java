package com.interviewquestion.presenter;

import android.content.Intent;

import com.interviewquestion.activity.QuestionActivity;
import com.interviewquestion.adapter.CategoryAdapter;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.fragment.CategoryFragment;
import com.interviewquestion.interactor.CategoryInteractor;
import com.interviewquestion.interactor.CategoryInteractorImpl;
import com.interviewquestion.repository.Question;
import com.interviewquestion.util.Constant;
import com.interviewquestion.view.CategoryView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28/9/16.
 */

public class CategoryPresenterImpl implements CategoryPresenter, CategoryInteractor.OnQuestionResponseListener {

    private WeakReference<CategoryView> categoryView;
    private CategoryInteractor categoryInteractor;
    private CategoryAdapter categoryAdapter;
    private List<Question.Response> questionList;

    public CategoryPresenterImpl(WeakReference<CategoryView> categoryView) {
        this.categoryView = categoryView;
        categoryInteractor = new CategoryInteractorImpl(((CategoryFragment) categoryView.get()).getContext());
    }

    @Override
    public void onDestroy() {
        categoryList.clear();
        questionList = null;
        categoryView.clear();
        categoryAdapter = null;
    }

    @Override
    public void prepareToFetchQuestionFromDB(int serviceType) {
        if (categoryView.get() != null) {
            CategoryFragment context = ((CategoryFragment) categoryView.get());

            categoryView.get().showProgress();
            switch (serviceType) {
                case Constant.ANDROID:

                    categoryInteractor.getAndroidQuestions(this);
                    break;

                case Constant.IOS:

                    categoryInteractor.getIosQuestion(this);
                    break;

                case Constant.JAVA:

                    categoryInteractor.getJavaQuestions(this);
                    break;
            }

        }
    }

    @Override
    public void showQuestions(int position) {
        if (position == 0) {
            DataHolder.getInstance().setQuestionList(questionList);
        } else {
            List<Question.Response> tempList = new ArrayList<>();
            for (Question.Response response : questionList) {
                if (response.getCategory().equalsIgnoreCase(categoryList.get(position))) {
                    tempList.add(response);
                }
            }
            DataHolder.getInstance().setQuestionList(tempList);
        }
        CategoryFragment context = ((CategoryFragment) categoryView.get());
        Intent intent = new Intent(context.getActivity(), QuestionActivity.class);
        intent.putExtra("title", categoryList.get(position));
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(List<Question.Response> questionList) {
        if (categoryView.get() != null) {
            updateUI(questionList);
            categoryView.get().hideProgress();
        }
    }

    @Override
    public void onError(String error) {
        if (categoryView.get() != null) {
            categoryView.get().hideProgress();
        }
    }

    @Override
    public void updateUI(List<Question.Response> responseList) {
        questionList = responseList;
        categoryList.clear();
        categoryList.add("All Question");
        for (Question.Response response : responseList) {
            if (!categoryList.contains(response.getCategory()))
                categoryList.add(response.getCategory());
        }

        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public CategoryAdapter initCategoryAdapter() {
        return categoryAdapter = new CategoryAdapter(categoryList, (CategoryFragment) categoryView.get());
    }
}
