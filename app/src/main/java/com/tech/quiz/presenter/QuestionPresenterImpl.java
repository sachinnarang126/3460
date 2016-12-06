package com.tech.quiz.presenter;

import android.content.Context;

import com.tech.quiz.adapter.QuestionPagerAdapter;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.models.databasemodel.Questions;
import com.tech.quiz.repositories.presenter.QuestionPresenter;
import com.tech.quiz.view.activity.QuestionActivity;
import com.tech.quiz.view.views.QuestionView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import library.mvp.MvpBasePresenter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by root on 28/9/16.
 */

public class QuestionPresenterImpl extends MvpBasePresenter<QuestionView> implements QuestionPresenter {

    private QuestionPagerAdapter questionPagerAdapter;

    public QuestionPresenterImpl(QuestionView view, Context context) {
        attachView(view, context);
    }

    @Override
    public void onCreate() {
        shuffleQuestion();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        questionPagerAdapter = null;
        detachView();
        shuffledQuestionList.clear();
    }

    @Override
    public void prepareListToShowAllQuestion() {
        if (isViewAttached()) {
            getView().showProgress();
            shuffledQuestionList.clear();
            Observable.from(DataHolder.getInstance().getQuestionList()).
                    subscribe(new Subscriber<Questions>() {
                        @Override
                        public void onCompleted() {
                            questionPagerAdapter.notifyDataSetChanged();
                            getView().hideProgress();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Questions questions) {
                            shuffledQuestionList.add(questions);
                        }
                    });
        }
    }

    @Override
    public void prepareListToShowUnansweredQuestion() {
        if (isViewAttached()) {
            getView().showProgress();
            shuffledQuestionList.clear();
            Observable.from(DataHolder.getInstance().getQuestionList()).
                    filter(new Func1<Questions, Boolean>() {
                        @Override
                        public Boolean call(Questions questions) {
                            return !questions.isAttempted();
                        }
                    }).
                    subscribe(new Subscriber<Questions>() {
                        @Override
                        public void onCompleted() {
                            questionPagerAdapter.notifyDataSetChanged();
                            getView().hideProgress();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Questions questions) {
                            shuffledQuestionList.add(questions);
                        }
                    });
        }
    }

    @Override
    public void prepareListToShowAnsweredQuestion() {
        if (isViewAttached()) {
            getView().showProgress();
            shuffledQuestionList.clear();
            Observable.from(DataHolder.getInstance().getQuestionList()).
                    filter(new Func1<Questions, Boolean>() {
                        @Override
                        public Boolean call(Questions questions) {
                            return questions.isAttempted();
                        }
                    }).
                    subscribe(new Subscriber<Questions>() {
                        @Override
                        public void onCompleted() {
                            questionPagerAdapter.notifyDataSetChanged();
                            getView().hideProgress();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Questions questions) {
                            shuffledQuestionList.add(questions);
                        }
                    });

        }
    }

    @Override
    public void prepareListToResetAll() {
        if (isViewAttached()) {
            getView().showProgress();
            shuffledQuestionList.clear();
            Observable.from(DataHolder.getInstance().getQuestionList()).
                    map(new Func1<Questions, Questions>() {
                        @Override
                        public Questions call(Questions questions) {
                            List<String> shuffledOptionList = new ArrayList<>();
                            questions.setAttempted(false);
                            questions.setCorrectAnswerProvided(false);
                            questions.setUserAnswer(0);
                            String answer = "";
                            switch (questions.getAnswer()) {
                                case "1":
                                    answer = questions.getA();
                                    break;

                                case "2":
                                    answer = questions.getB();
                                    break;

                                case "3":
                                    answer = questions.getC();
                                    break;

                                case "4":
                                    answer = questions.getD();
                                    break;
                            }

                            shuffledOptionList.add(questions.getA());
                            shuffledOptionList.add(questions.getB());
                            shuffledOptionList.add(questions.getC());
                            shuffledOptionList.add(questions.getD());

                            Collections.shuffle(shuffledOptionList);

                            int answerIndex = shuffledOptionList.indexOf(answer);

                            questions.setAnswer(String.valueOf(answerIndex + 1));

                            questions.setA(shuffledOptionList.get(0));
                            questions.setB(shuffledOptionList.get(1));
                            questions.setC(shuffledOptionList.get(2));
                            questions.setD(shuffledOptionList.get(3));

                            return questions;
                        }
                    }).
                    subscribe(new Subscriber<Questions>() {
                        @Override
                        public void onCompleted() {
                            questionPagerAdapter.notifyDataSetChanged();
                            getView().hideProgress();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Questions questions) {
                            shuffledQuestionList.add(questions);
                        }
                    });

        }
    }

    @Override
    public void shuffleQuestion() {
        Observable.from(DataHolder.getInstance().getQuestionList()).
                map(new Func1<Questions, Questions>() {
                    @Override
                    public Questions call(Questions question) {

                        List<String> shuffledOptionList = new ArrayList<>();
                        if (!question.isAttempted()) {
                            String answer = "";
                            switch (question.getAnswer()) {
                                case "1":
                                    answer = question.getA();
                                    break;

                                case "2":
                                    answer = question.getB();
                                    break;

                                case "3":
                                    answer = question.getC();
                                    break;

                                case "4":
                                    answer = question.getD();
                                    break;
                            }

                            shuffledOptionList.add(question.getA());
                            shuffledOptionList.add(question.getB());
                            shuffledOptionList.add(question.getC());
                            shuffledOptionList.add(question.getD());

                            Collections.shuffle(shuffledOptionList);

                            int answerIndex = shuffledOptionList.indexOf(answer);

                            question.setAnswer(String.valueOf(answerIndex + 1));

                            question.setA(shuffledOptionList.get(0));
                            question.setB(shuffledOptionList.get(1));
                            question.setC(shuffledOptionList.get(2));
                            question.setD(shuffledOptionList.get(3));
                        }

                        return question;
                    }
                }).
                subscribe(new Subscriber<Questions>() {
                    @Override
                    public void onCompleted() {
                        Collections.shuffle(shuffledQuestionList);
                        DataHolder.getInstance().setShuffledQuestionList(shuffledQuestionList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Questions questions) {
                        shuffledQuestionList.add(questions);
                    }
                });
    }

    @Override
    public void shuffleQuestionAndResetAllLocally() {
        Observable.from(shuffledQuestionList).
                map(new Func1<Questions, Questions>() {
                    @Override
                    public Questions call(Questions questions) {
                        List<String> shuffledOptionList = new ArrayList<>();
                        questions.setAttempted(false);
                        questions.setCorrectAnswerProvided(false);
                        questions.setUserAnswer(0);
                        String answer = "";
                        switch (questions.getAnswer()) {
                            case "1":
                                answer = questions.getA();
                                break;

                            case "2":
                                answer = questions.getB();
                                break;

                            case "3":
                                answer = questions.getC();
                                break;

                            case "4":
                                answer = questions.getD();
                                break;
                        }

                        shuffledOptionList.add(questions.getA());
                        shuffledOptionList.add(questions.getB());
                        shuffledOptionList.add(questions.getC());
                        shuffledOptionList.add(questions.getD());

                        Collections.shuffle(shuffledOptionList);

                        int answerIndex = shuffledOptionList.indexOf(answer);

                        questions.setAnswer(String.valueOf(answerIndex + 1));

                        questions.setA(shuffledOptionList.get(0));
                        questions.setB(shuffledOptionList.get(1));
                        questions.setC(shuffledOptionList.get(2));
                        questions.setD(shuffledOptionList.get(3));

                        return questions;
                    }
                }).subscribe();
    }

    @Override
    public QuestionPagerAdapter initAdapter(int technology) {
        return questionPagerAdapter = new QuestionPagerAdapter(((QuestionActivity) getContext()).getSupportFragmentManager(),
                shuffledQuestionList, technology);
    }
}
