package com.tech.quiz.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.tech.R;
import com.tech.quiz.adapter.PagerAdapter;
import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.interactor.HomeInterActorImpl;
import com.tech.quiz.models.bean.QuestionResponse;
import com.tech.quiz.models.databasemodel.Android;
import com.tech.quiz.models.databasemodel.Ios;
import com.tech.quiz.models.databasemodel.Java;
import com.tech.quiz.network.RetrofitApiService;
import com.tech.quiz.network.RetrofitClient;
import com.tech.quiz.repositories.interactor.HomeInterActor;
import com.tech.quiz.repositories.presenter.HomePresenter;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.HomeActivity;
import com.tech.quiz.view.views.HomeView;

import java.util.ArrayList;
import java.util.List;

import library.mvp.ActivityPresenter;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * @author Sachin Narang
 */

public class HomePresenterImpl extends ActivityPresenter<HomeView, HomeInterActor> implements HomePresenter,
        HomeInterActor.OnIosQuestionResponseListener, HomeInterActor.OnAndroidQuestionResponseListener,
        HomeInterActor.OnJavaQuestionResponseListener, SearchView.OnQueryTextListener,
        ViewPager.OnPageChangeListener, MenuItemCompat.OnActionExpandListener, SearchViewCompat.OnCloseListener {

    private PagerAdapter pagerAdapter;
    private int position;

    public HomePresenterImpl(HomeView view, Context context) {
        super(view, context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareToFetchQuestion();
    }

    @Override
    public void prepareToFetchQuestion() {
        if (hasToFetchQuestionFromServer() && isViewAttached()) {

            if (((HomeActivity) getContext()).isInternetAvailable()) {

                DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(getContext());
                List<Integer> androidIdList = databaseManager.getAndroidIdList();
                List<Integer> iosIdList = databaseManager.getIosIdList();
                List<Integer> javaIdList = databaseManager.getJavaIdList();

                RetrofitApiService apiService = RetrofitClient.getRetrofitClient();

                Observable<QuestionResponse> androidQuestion = apiService.getAndroidSelectedQuestion(androidIdList);
                Observable<QuestionResponse> javaQuestion = apiService.getJavaSelectedQuestion(javaIdList);
                Observable<QuestionResponse> iosQuestion = apiService.getIosSelectedQuestion(iosIdList);

                unSubscribeFromSubscriptionIfSubscribed(Constant.ANDROID_POST_URL);
                unSubscribeFromSubscriptionIfSubscribed(Constant.IOS_POST_URL);
                unSubscribeFromSubscriptionIfSubscribed(Constant.JAVA_POST_URL);

                putSubscriberInMap(getInterActor().getAndroidQuestions(this, androidQuestion), Constant.ANDROID_POST_URL);
                putSubscriberInMap(getInterActor().getIosQuestion(this, iosQuestion), Constant.IOS_POST_URL);
                putSubscriberInMap(getInterActor().getJavaQuestions(this, javaQuestion), Constant.JAVA_POST_URL);

            } else {
                onError(getString(R.string.error_internet_first_launch));
            }
        }
    }

    @Override
    public PagerAdapter initAdapter(FragmentManager fm) {
        return pagerAdapter = new PagerAdapter(fm);
    }

    @Override
    public void onSuccess(List<QuestionResponse.Response> questionList, int serviceType) {
        saveDataToDB(questionList, serviceType);
        saveTimeToPreference();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    synchronized public void saveDataToDB(List<QuestionResponse.Response> questionList, int serviceType) {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(getContext());
        switch (serviceType) {
            case Constant.ANDROID:
                saveAndroidQuestion(databaseManager, questionList);
                break;

            case Constant.IOS:
                saveIosQuestion(databaseManager, questionList);
                break;

            case Constant.JAVA:
                saveJavaQuestion(databaseManager, questionList);
                break;
        }
    }

    @Override
    public void saveTimeToPreference() {
        DataHolder.getInstance().getPreferences(getContext()).edit().
                putLong(Constant.UPDATED_QUESTION_TIME_IN_MILLIS, System.currentTimeMillis()).apply();
    }

    @Override
    public boolean hasToFetchQuestionFromServer() {
        long twoHour = 1000 * 60 * 60 * 2;
        long savedTime = DataHolder.getInstance().getPreferences(getContext()).
                getLong(Constant.UPDATED_QUESTION_TIME_IN_MILLIS, 0);

        return System.currentTimeMillis() - savedTime > twoHour;
    }

    private void saveAndroidQuestion(final DatabaseManager databaseManager, List<QuestionResponse.Response> questionList) {
        final List<Android> androidList = new ArrayList<>();

        Observable.from(questionList).
                map(new Func1<QuestionResponse.Response, Android>() {
                    @Override
                    public Android call(QuestionResponse.Response question) {
                        Android android = new Android();
                        android.setQuestionId(Integer.parseInt(question.getId()));
                        android.setUserLevel(Integer.parseInt(question.getUserLevel()));
                        android.setCategory(question.getCategory());
                        android.setQuestion(question.getQuestion());
                        android.setA(question.getA());
                        android.setB(question.getB());
                        android.setC(question.getC());
                        android.setD(question.getD());
                        android.setAnswer(question.getAnswer());
                        return android;
                    }
                }).
                subscribe(new Observer<Android>() {
                    @Override
                    public void onCompleted() {
                        databaseManager.saveQuestionToAndroidTable(androidList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Android android) {
                        androidList.add(android);
                    }
                });
    }

    private void saveIosQuestion(final DatabaseManager databaseManager, List<QuestionResponse.Response> questionList) {
        final List<Ios> iosList = new ArrayList<>();
        Observable.from(questionList).
                map(new Func1<QuestionResponse.Response, Ios>() {
                    @Override
                    public Ios call(QuestionResponse.Response question) {
                        Ios ios = new Ios();
                        ios.setQuestionId(Integer.parseInt(question.getId()));
                        ios.setUserLevel(Integer.parseInt(question.getUserLevel()));
                        ios.setCategory(question.getCategory());
                        ios.setQuestion(question.getQuestion());
                        ios.setA(question.getA());
                        ios.setB(question.getB());
                        ios.setC(question.getC());
                        ios.setD(question.getD());
                        ios.setAnswer(question.getAnswer());
                        return ios;
                    }
                }).
                subscribe(new Observer<Ios>() {
                    @Override
                    public void onCompleted() {
                        databaseManager.saveQuestionToIosTable(iosList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Ios ios) {
                        iosList.add(ios);
                    }
                });
    }

    private void saveJavaQuestion(final DatabaseManager databaseManager, List<QuestionResponse.Response> questionList) {
        final List<Java> javaList = new ArrayList<>();
        Observable.from(questionList).
                map(new Func1<QuestionResponse.Response, Java>() {
                    @Override
                    public Java call(QuestionResponse.Response question) {
                        Java java = new Java();
                        java.setQuestionId(Integer.parseInt(question.getId()));
                        java.setUserLevel(Integer.parseInt(question.getUserLevel()));
                        java.setCategory(question.getCategory());
                        java.setQuestion(question.getQuestion());
                        java.setA(question.getA());
                        java.setB(question.getB());
                        java.setC(question.getC());
                        java.setD(question.getD());
                        java.setAnswer(question.getAnswer());
                        return java;
                    }
                }).
                subscribe(new Observer<Java>() {
                    @Override
                    public void onCompleted() {
                        databaseManager.saveQuestionToJavaTable(javaList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Java java) {
                        javaList.add(java);
                    }
                });
    }

    @Override
    protected HomeInterActor createInterActor() {
        return new HomeInterActorImpl();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        pagerAdapter.getCategoryInstance(position).getPresenter().searchCategory(newText);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getView().getToolBar().collapseActionView();
        this.position = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * Called when a menu item
     * is expanded.
     *
     * @param item Item that was expanded
     * @return true if the item should expand, false if expansion should be suppressed.
     */
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        setItemsVisibility(item, false);
        return true;
    }

    /**
     * Called when a menu item
     * is collapsed.
     *
     * @param item Item that was collapsed
     * @return true if the item should collapse, false if collapsing should be suppressed.
     */
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        setItemsVisibility(item, true);
        pagerAdapter.getCategoryInstance(position).getPresenter().searchCategory("");
        return true;
    }

    /**
     * The user is attempting to close the SearchView.
     *
     * @return true if the listener wants to override the default behavior of clearing the
     * text field and dismissing it, false otherwise.
     */
    @Override
    public boolean onClose() {
        return false;
    }

    private void setItemsVisibility(MenuItem exception, boolean visible) {
        Menu menu = getView().getOptionMenu();
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) {
                item.setVisible(visible);
            }
        }
    }
}
