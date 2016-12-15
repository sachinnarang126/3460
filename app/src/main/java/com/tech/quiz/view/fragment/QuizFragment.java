package com.tech.quiz.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.R;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.models.databasemodel.Questions;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.QuestionActivity;

/**
 * @author Sachin Narang
 */

public class QuizFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private Questions question;

    public static QuizFragment getInstance(int pos, int total) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        bundle.putInt("total", total);
        QuizFragment quizFragment = new QuizFragment();
        quizFragment.setArguments(bundle);
        return quizFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        Bundle bundle = getArguments();
        question = DataHolder.getInstance().getShuffledQuestionList().get(bundle.getInt("pos"));

        TextView txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        TextView txtViewA = (TextView) view.findViewById(R.id.txtViewA);
        TextView txtViewB = (TextView) view.findViewById(R.id.txtViewB);
        TextView txtViewC = (TextView) view.findViewById(R.id.txtViewC);
        TextView txtViewD = (TextView) view.findViewById(R.id.txtViewD);
        TextView txtCount = (TextView) view.findViewById(R.id.txtCount);

        txtViewA.setMovementMethod(new ScrollingMovementMethod());
        txtViewB.setMovementMethod(new ScrollingMovementMethod());
        txtViewC.setMovementMethod(new ScrollingMovementMethod());
        txtViewD.setMovementMethod(new ScrollingMovementMethod());

        txtQuestion.setText(question.getQuestion());

        txtViewA.setText(question.getA());
        txtViewB.setText(question.getB());
        txtViewC.setText(question.getC());
        txtViewD.setText(question.getD());
        String count = (bundle.getInt("pos") + 1) + "/" + bundle.getInt("total");
        txtCount.setText(count);

        txtViewA.setOnClickListener(this);
        txtViewB.setOnClickListener(this);
        txtViewC.setOnClickListener(this);
        txtViewD.setOnClickListener(this);

        txtViewA.setOnLongClickListener(this);
        txtViewB.setOnLongClickListener(this);
        txtViewC.setOnLongClickListener(this);
        txtViewD.setOnLongClickListener(this);

        if (question.isAttempted()) {
            setAnswer(txtViewA, txtViewB, txtViewC, txtViewD);
        }
    }

    private void setAnswer(TextView txtViewA, TextView txtViewB, TextView txtViewC, TextView txtViewD) {
        switch (question.getUserAnswer()) {

            case Constant.OPTION_A:
                txtViewA.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                txtViewA.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;

            case Constant.OPTION_B:
                txtViewB.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                txtViewB.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;

            case Constant.OPTION_C:
                txtViewC.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                txtViewC.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;

            case Constant.OPTION_D:
                txtViewD.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                txtViewD.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;
        }
    }

    private void updateViewAccordingToAnswer(int selectedAnswer, TextView textView) {
        final int answer = Integer.parseInt(question.getAnswer());
        question.setUserAnswer(selectedAnswer);

        if (selectedAnswer == answer)
            question.setCorrectAnswerProvided(true);
        else question.setCorrectAnswerProvided(false);


        if (!question.isAttempted()) {
            question.setAttempted(true);
            ((QuestionActivity) getActivity()).getPresenter().checkForQuizCompletion();
        }

        textView.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        textView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        if (getView() != null) {
            TextView txtViewA, txtViewB, txtViewC, txtViewD;
            switch (selectedAnswer) {
                case Constant.OPTION_A:
                    txtViewB = ((TextView) getView().findViewById(R.id.txtViewB));
                    txtViewC = ((TextView) getView().findViewById(R.id.txtViewC));
                    txtViewD = ((TextView) getView().findViewById(R.id.txtViewD));

                    txtViewB.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewB.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));

                    txtViewC.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewC.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));

                    txtViewD.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewD.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                    break;

                case Constant.OPTION_B:
                    txtViewA = ((TextView) getView().findViewById(R.id.txtViewA));
                    txtViewC = ((TextView) getView().findViewById(R.id.txtViewC));
                    txtViewD = ((TextView) getView().findViewById(R.id.txtViewD));

                    txtViewA.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewA.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));

                    txtViewC.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewC.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));

                    txtViewD.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewD.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                    break;

                case Constant.OPTION_C:
                    txtViewA = ((TextView) getView().findViewById(R.id.txtViewA));
                    txtViewB = ((TextView) getView().findViewById(R.id.txtViewB));
                    txtViewD = ((TextView) getView().findViewById(R.id.txtViewD));

                    txtViewA.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewA.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));

                    txtViewB.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewB.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));

                    txtViewD.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewD.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                    break;

                case Constant.OPTION_D:
                    txtViewA = ((TextView) getView().findViewById(R.id.txtViewA));
                    txtViewB = ((TextView) getView().findViewById(R.id.txtViewB));
                    txtViewC = ((TextView) getView().findViewById(R.id.txtViewC));

                    txtViewA.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewA.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));

                    txtViewB.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewB.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));

                    txtViewC.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
                    txtViewC.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtViewA:
                updateViewAccordingToAnswer(Constant.OPTION_A, (TextView) view);
                break;

            case R.id.txtViewB:
                updateViewAccordingToAnswer(Constant.OPTION_B, (TextView) view);
                break;

            case R.id.txtViewC:
                updateViewAccordingToAnswer(Constant.OPTION_C, (TextView) view);
                break;

            case R.id.txtViewD:
                updateViewAccordingToAnswer(Constant.OPTION_D, (TextView) view);
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.txtViewA:
                showOptionDialog(question.getA(), "Option-A");
                break;

            case R.id.txtViewB:
                showOptionDialog(question.getB(), "Option-B");
                break;

            case R.id.txtViewC:
                showOptionDialog(question.getC(), "Option-C");
                break;

            case R.id.txtViewD:
                showOptionDialog(question.getD(), "Option-D");
                break;
        }
        return false;
    }

    public void showOptionDialog(String message, String title) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
