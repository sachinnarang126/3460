package library.mvp;

/**
 * @author Sachin Narang
 */

interface LifeCyclePresenter {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
