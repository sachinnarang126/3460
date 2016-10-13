package com.interviewquestion.presenter;

import android.content.Intent;

import com.interviewquestion.activity.QuestionActivity;
import com.interviewquestion.adapter.CategoryAdapter;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.fragment.CategoryFragment;
import com.interviewquestion.interactor.CategoryInteractorImpl;
import com.interviewquestion.models.databasemodel.Questions;
import com.interviewquestion.repositories.interactor.CategoryInteractor;
import com.interviewquestion.repositories.presenter.CategoryPresenter;
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
    private List<Questions> questionList;

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
            List<Questions> tempList = new ArrayList<>();
            for (Questions questions : questionList) {
                if (questions.getCategory().equalsIgnoreCase(categoryList.get(position))) {
                    tempList.add(questions);
                }
            }
            DataHolder.getInstance().setQuestionList(tempList);
        }
        CategoryFragment context = ((CategoryFragment) categoryView.get());
        Intent intent = new Intent(context.getActivity(), QuestionActivity.class);
        intent.putExtra("title", categoryList.get(position));
        intent.putExtra("technology", ((CategoryFragment) categoryView.get()).getArguments().getInt("serviceType"));
        context.startActivity(intent);
    }

    @Override
    public <T extends Questions> void onSuccess(List<T> questionListFromDB) {
        if (categoryView.get() != null) {
            updateUI(castToQuestions(questionListFromDB));
            categoryView.get().hideProgress();
        }
    }

    @Override
    public void onError(String error) {
        if (categoryView.get() != null) {
            categoryView.get().hideProgress();
            categoryView.get().onError(error);
        }
    }

    @Override
    public void updateUI(List<Questions> responseList) {
        questionList = responseList;
        categoryList.clear();
        categoryList.add("All Question");
        for (Questions questions : responseList) {
            if (!categoryList.contains(questions.getCategory()))
                categoryList.add(questions.getCategory());
        }

        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public CategoryAdapter initCategoryAdapter() {
        return categoryAdapter = new CategoryAdapter(categoryList, (CategoryFragment) categoryView.get());
    }

    @Override
    public void clearCategoryAdapter() {
        categoryList.clear();
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public <T extends Questions> List<Questions> castToQuestions(List<T> questionListFromDB) {
        List<Questions> questionsList = new ArrayList<>();
        for (T t : questionListFromDB) {
            questionsList.add(t);
        }
        return questionsList;
    }


}
