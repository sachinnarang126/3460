package com.tech.quiz.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.tech.quiz.adapter.BaseAdapter;
import com.tech.quiz.adapter.QuestionPagerAdapter;
import com.tech.quiz.adapter.QuizPagerAdapter;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.models.databasemodel.Questions;
import com.tech.quiz.repositories.presenter.QuestionPresenter;
import com.tech.quiz.view.activity.QuestionActivity;
import com.tech.quiz.view.views.QuestionView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import library.mvp.ActivityPresenter;
import library.mvp.IBaseInterActor;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Sachin Narang
 */

public class QuestionPresenterImpl extends ActivityPresenter<QuestionView, IBaseInterActor> implements QuestionPresenter {

    private BaseAdapter questionPagerAdapter;
    private int attemptedQuestion = 0;

    public QuestionPresenterImpl(QuestionView view, Context context) {
        attachView(view, context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        questionPagerAdapter = null;
        shuffledQuestionList.clear();
    }

    @Override
    public void prepareListToShowAllQuestion() {
        if (isViewAttached()) {
            getView().showProgress();
            shuffledQuestionList.clear();
            questionPagerAdapter.notifyDataSetChanged();

            Observable.from(DataHolder.getInstance().getQuestionList()).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Subscriber<Questions>() {
                        @Override
                        public void onCompleted() {
                            questionPagerAdapter.notifyDataSetChanged();
                            getView().hideProgress();
                        }

                        @Override
                        public void onError(Throwable e) {
                            getView().hideProgress();
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
            questionPagerAdapter.notifyDataSetChanged();

            Observable.from(DataHolder.getInstance().getQuestionList()).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
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
                            getView().hideProgress();
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
            questionPagerAdapter.notifyDataSetChanged();

            Observable.from(DataHolder.getInstance().getQuestionList()).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
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
                            getView().hideProgress();
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
            questionPagerAdapter.notifyDataSetChanged();

            Observable.from(DataHolder.getInstance().getQuestionList()).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
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
                            getView().hideProgress();
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
        getView().showProgress();
        final List<Questions> tempList = new ArrayList<>();
        Observable.from(DataHolder.getInstance().getQuestionList()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
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
                        shuffledQuestionList.addAll(tempList);
                        Collections.shuffle(shuffledQuestionList);
                        DataHolder.getInstance().setShuffledQuestionList(shuffledQuestionList);
                        questionPagerAdapter.notifyDataSetChanged();
                        getView().hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                    }

                    @Override
                    public void onNext(Questions questions) {
                        tempList.add(questions);
                    }
                });
    }

    @Override
    public void showResult() {
        final int totalQuestion = shuffledQuestionList.size();
        final int[] correctAnswerCount = {0};
        Observable.from(shuffledQuestionList).
                filter(new Func1<Questions, Boolean>() {
                    @Override
                    public Boolean call(Questions questions) {
                        return questions.isCorrectAnswerProvided();
                    }
                }).
                map(new Func1<Questions, Integer>() {
                    @Override
                    public Integer call(Questions questions) {
                        return correctAnswerCount[0]++;
                    }
                }).
                subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        new AlertDialog.Builder(getContext())
                                .setMessage("Total Question: " + totalQuestion + "\n" +
                                        "Attempted Question: " + attemptedQuestion + "\n\n" +
                                        "Correct Answer: " + correctAnswerCount[0] + "\n" +
                                        "In-Correct Answer: " + (totalQuestion - correctAnswerCount[0]))
                                .setTitle(getView().getTechnology() + " Quiz Result")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ((QuestionActivity) getContext()).finish();
                                    }
                                })
                                .create()
                                .show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
    }

    @Override
    public BaseAdapter initAdapter(int technology) {
        return questionPagerAdapter = new QuestionPagerAdapter(((QuestionActivity) getContext()).getSupportFragmentManager(),
                shuffledQuestionList, technology);
    }

    @Override
    public BaseAdapter initAdapter() {
        return questionPagerAdapter = new QuizPagerAdapter(((QuestionActivity) getContext()).getSupportFragmentManager(),
                shuffledQuestionList);
    }

    @Override
    public void checkForQuizCompletion() {
        attemptedQuestion++;
        if (attemptedQuestion == shuffledQuestionList.size()) {
            showResult();
        }
    }

    @Override
    protected IBaseInterActor createInterActor() {
        return null;
    }
}
