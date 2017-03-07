package com.tech.quiz.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tech.R;
import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.models.databasemodel.Questions;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.QuestionActivity;

/**
 * @author Sachin Narang
 */

public class QuestionFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private int position;
    private Questions question;

    public static QuestionFragment getInstance(int pos, int total, int technology) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        bundle.putInt("total", total);
        bundle.putInt("technology", technology);
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(bundle);
        return questionFragment;
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
        position = bundle.getInt("pos");
        int total = bundle.getInt("total");
        question = DataHolder.getInstance().getShuffledQuestionList().get(position);

        TextView txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        TextView txtViewA = (TextView) view.findViewById(R.id.txtViewA);
        TextView txtViewB = (TextView) view.findViewById(R.id.txtViewB);
        TextView txtViewC = (TextView) view.findViewById(R.id.txtViewC);
        TextView txtViewD = (TextView) view.findViewById(R.id.txtViewD);
        TextView txtCount = (TextView) view.findViewById(R.id.txtCount);

        ImageView imgLeft = (ImageView) view.findViewById(R.id.imgLeft);
        ImageView imgRight = (ImageView) view.findViewById(R.id.imgRight);

        if (total == 1) {
            imgLeft.setVisibility(View.INVISIBLE);
            imgRight.setVisibility(View.INVISIBLE);
        } else if (position == 0) {
            imgLeft.setVisibility(View.INVISIBLE);
            imgRight.setVisibility(View.VISIBLE);
        } else if (position + 1 == total) {
            imgLeft.setVisibility(View.VISIBLE);
            imgRight.setVisibility(View.INVISIBLE);
        }

        txtViewA.setMovementMethod(new ScrollingMovementMethod());
        txtViewB.setMovementMethod(new ScrollingMovementMethod());
        txtViewC.setMovementMethod(new ScrollingMovementMethod());
        txtViewD.setMovementMethod(new ScrollingMovementMethod());
        txtQuestion.setMovementMethod(new ScrollingMovementMethod());

        txtQuestion.setText(question.getQuestion());
        txtQuestion.setTextIsSelectable(true);

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

        imgRight.setOnClickListener(this);
        imgLeft.setOnClickListener(this);

        System.out.println("question.isAttempted() " + question.isAttempted());
        if (question.isAttempted()) {
            TextView txtUserValue = (TextView) view.findViewById(R.id.txtUserValue);
            setUiCorrectAnswer(txtViewA, txtViewB, txtViewC, txtViewD);
            if (!question.isCorrectAnswerProvided()) {
                setUiForIncorrectAnswer(txtViewA, txtViewB, txtViewC, txtViewD);
                txtUserValue.setText(getString(R.string.incorrect));
            } else {
                txtUserValue.setText(getString(R.string.correct));
            }
        }
    }

    private void setUiCorrectAnswer(TextView txtViewA, TextView txtViewB, TextView txtViewC, TextView txtViewD) {
        switch (Integer.parseInt(question.getAnswer())) {
            case Constant.OPTION_A:
                txtViewA.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                txtViewA.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;

            case Constant.OPTION_B:
                txtViewB.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                txtViewB.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;

            case Constant.OPTION_C:
                txtViewC.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                txtViewC.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;

            case Constant.OPTION_D:
                txtViewD.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                txtViewD.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;
        }
    }

    private void setUiForIncorrectAnswer(TextView txtViewA, TextView txtViewB, TextView txtViewC, TextView txtViewD) {
        switch (question.getUserAnswer()) {
            case Constant.OPTION_A:
                txtViewA.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                txtViewA.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;

            case Constant.OPTION_B:
                txtViewB.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                txtViewB.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;

            case Constant.OPTION_C:
                txtViewC.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                txtViewC.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;

            case Constant.OPTION_D:
                txtViewD.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                txtViewD.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                break;
        }
    }

    private void updateViewAccordingToAnswer(int selectedAnswer, TextView textView) {

        final int answer = Integer.parseInt(question.getAnswer());
        textView.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));

        question.setAttempted(true);
        ((QuestionActivity) getActivity()).getPresenter().increaseAttemptedQuestionCount();
        question.setUserAnswer(selectedAnswer);
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(getActivity());

        if (selectedAnswer == answer) {
            textView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
            if (getView() != null)
                ((TextView) getView().findViewById(R.id.txtUserValue)).setText(getString(R.string.correct));

            question.setCorrectAnswerProvided(true);
        } else {
            question.setCorrectAnswerProvided(false);
            textView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getView() != null)
                        ((TextView) getView().findViewById(R.id.txtUserValue)).setText(getString(R.string.incorrect));

                    switch (answer) {
                        case Constant.OPTION_A:
                            TextView txtViewA = ((TextView) getView().findViewById(R.id.txtViewA));
                            txtViewA.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                            txtViewA.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                            break;

                        case Constant.OPTION_B:
                            TextView txtViewB = ((TextView) getView().findViewById(R.id.txtViewB));
                            txtViewB.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                            txtViewB.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                            break;

                        case Constant.OPTION_C:
                            TextView txtViewC = ((TextView) getView().findViewById(R.id.txtViewC));
                            txtViewC.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                            txtViewC.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                            break;

                        case Constant.OPTION_D:
                            TextView txtViewD = ((TextView) getView().findViewById(R.id.txtViewD));
                            txtViewD.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                            txtViewD.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                            break;
                    }
                }
            }, 100);
        }
        updateSelectionInToDB(databaseManager);
    }

    private void updateSelectionInToDB(DatabaseManager databaseManager) {
        switch (getArguments().getInt("technology")) {
            case Constant.ANDROID:
                databaseManager.updateAndroidQuestion((Android) question);
                break;

            case Constant.IOS:
                databaseManager.updateIosQuestion((Ios) question);
                break;

            case Constant.JAVA:
                databaseManager.updateJavaQuestion((Java) question);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtViewA:
                if (!question.isAttempted()) {
                    question.setAttempted(true);
                    updateViewAccordingToAnswer(Constant.OPTION_A, (TextView) view);
                }
                break;

            case R.id.txtViewB:
                if (!question.isAttempted()) {
                    question.setAttempted(true);
                    updateViewAccordingToAnswer(Constant.OPTION_B, (TextView) view);
                }
                break;

            case R.id.txtViewC:
                if (!question.isAttempted()) {
                    question.setAttempted(true);
                    updateViewAccordingToAnswer(Constant.OPTION_C, (TextView) view);
                }
                break;

            case R.id.txtViewD:
                if (!question.isAttempted()) {
                    question.setAttempted(true);
                    updateViewAccordingToAnswer(Constant.OPTION_D, (TextView) view);
                }
                break;

            case R.id.imgLeft:
                ((QuestionActivity) getActivity()).getPresenter().setSelectionOfViewPager(position - 1);
                break;

            case R.id.imgRight:
                ((QuestionActivity) getActivity()).getPresenter().setSelectionOfViewPager(position + 1);
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

    private void showOptionDialog(String message, String title) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
