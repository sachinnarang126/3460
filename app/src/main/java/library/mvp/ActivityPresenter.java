package library.mvp;

/**
 * @author Sachin Narang
 */

abstract public class ActivityPresenter<V extends BaseView, T extends IBaseInterActor> extends BasePresenter<V, T> implements IPresenter<V> {

    @Override
    public void onCreate() {

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
        detachView();
    }
}
