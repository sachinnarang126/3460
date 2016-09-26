package com.interviewquestion.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.interviewquestion.R;
import com.interviewquestion.basecontroller.AppCompatFragment;
import com.interviewquestion.dataholder.DataHolder;
import com.interviewquestion.repository.Question;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends AppCompatFragment implements CompoundButton.OnCheckedChangeListener {
    private Question.Response question;
    private boolean isChecked;

    public static QuestionFragment getInstance(int pos, int total) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        bundle.putInt("total", total);
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
        Bundle bundle = getArguments();
        question = DataHolder.getInstance().getQuestionList().get(bundle.getInt("pos"));

        TextView txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        TextView txtViewA = (TextView) view.findViewById(R.id.txtViewA);
        TextView txtViewB = (TextView) view.findViewById(R.id.txtViewB);
        TextView txtViewC = (TextView) view.findViewById(R.id.txtViewC);
        TextView txtViewD = (TextView) view.findViewById(R.id.txtViewD);
        TextView txtCount = (TextView) view.findViewById(R.id.txtCount);

        txtQuestion.setText(question.getQuestion());
        txtViewA.setText(question.getA());
        txtViewB.setText(question.getB());
        txtViewC.setText(question.getC());
        txtViewD.setText(question.getD());
        String count = (bundle.getInt("pos") + 1) + "/" + bundle.getInt("total");
        txtCount.setText(count);

        CheckBox checkBoxA = (CheckBox) view.findViewById(R.id.checkboxA);
        CheckBox checkBoxB = (CheckBox) view.findViewById(R.id.checkboxB);
        CheckBox checkBoxC = (CheckBox) view.findViewById(R.id.checkboxC);
        CheckBox checkBoxD = (CheckBox) view.findViewById(R.id.checkboxD);

        checkBoxA.setOnCheckedChangeListener(this);
        checkBoxB.setOnCheckedChangeListener(this);
        checkBoxC.setOnCheckedChangeListener(this);
        checkBoxD.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!isChecked) {
            isChecked = true;
            switch (compoundButton.getId()) {
                case R.id.checkboxA:
                    updateViewAccordingToAnswer(1, getView().findViewById(R.id.parentA), ((TextView) getView().findViewById(R.id.txtViewA)));
                    break;

                case R.id.checkboxB:
                    updateViewAccordingToAnswer(2, getView().findViewById(R.id.parentB), ((TextView) getView().findViewById(R.id.txtViewB)));
                    break;

                case R.id.checkboxC:
                    updateViewAccordingToAnswer(3, getView().findViewById(R.id.parentC), ((TextView) getView().findViewById(R.id.txtViewC)));
                    break;

                case R.id.checkboxD:
                    updateViewAccordingToAnswer(4, getView().findViewById(R.id.parentD), ((TextView) getView().findViewById(R.id.txtViewD)));
                    break;
            }
        }
    }

    private void updateViewAccordingToAnswer(int selectedCheckBox, View view, TextView textView) {
        int answer = Integer.parseInt(question.getAnswer());
        textView.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        if (selectedCheckBox == answer) {
            view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
            ((TextView) getView().findViewById(R.id.txtUserValue)).setText("Correct");
        } else {
            view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
            ((TextView) getView().findViewById(R.id.txtUserValue)).setText("Incorrect");

            switch (answer) {
                case 1:
                    getView().findViewById(R.id.parentA).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                    ((TextView) getView().findViewById(R.id.txtViewA)).setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                    break;

                case 2:
                    getView().findViewById(R.id.parentB).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                    ((TextView) getView().findViewById(R.id.txtViewB)).setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                    break;

                case 3:
                    getView().findViewById(R.id.parentC).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                    ((TextView) getView().findViewById(R.id.txtViewC)).setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                    break;

                case 4:
                    getView().findViewById(R.id.parentD).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                    ((TextView) getView().findViewById(R.id.txtViewD)).setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
                    break;
            }
        }
    }
}
