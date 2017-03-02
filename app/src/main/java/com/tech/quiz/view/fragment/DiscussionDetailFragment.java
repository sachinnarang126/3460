package com.tech.quiz.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.R;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.models.bean.Discussion;
import com.tech.quiz.presenter.DiscussionDetailPresenterImpl;
import com.tech.quiz.view.activity.DiscussionActivity;
import com.tech.quiz.view.views.DiscussionDetailView;

import library.basecontroller.AppCompatFragment;

public class DiscussionDetailFragment extends AppCompatFragment<DiscussionDetailPresenterImpl> implements DiscussionDetailView {

    public static DiscussionDetailFragment getInstance() {
        return new DiscussionDetailFragment();
    }

    /**
     * In child fragment you must provide presenter implementation to this,
     * otherwise it will give a null pointer exception
     *
     * @return return the presenterImp instance
     */
    @Override
    protected DiscussionDetailPresenterImpl onAttachPresenter() {
        return new DiscussionDetailPresenterImpl(this, getContext());
    }

    /**
     * initialized the ui component
     *
     * @param view view inflated from xml
     */
    @SuppressWarnings("All")
    @Override
    protected void initUI(View view) {
        Discussion.Response response = DataHolder.getInstance().getDiscussion();
        ((DiscussionActivity) getActivity()).getSupportActionBar().setTitle(response.getTechnology().toUpperCase());

        TextView txtAnswer = (TextView) view.findViewById(R.id.txtAnswer);
        TextView txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        String answer = getString(R.string.answer) + response.getAnswer();
        txtAnswer.setText(answer);
        String question = getString(R.string.question) + response.getQuestion();
        txtQuestion.setText(question);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_discussion_detail, container, false);
    }

    @SuppressWarnings("All")
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((DiscussionActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.action_discussion));
    }
}
