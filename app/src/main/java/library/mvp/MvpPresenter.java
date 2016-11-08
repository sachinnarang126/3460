package library.mvp;

import android.support.annotation.UiThread;

/**
 * The base interface for each mvp presenter.
 *
 * @author Sachin Narang
 */

public interface MvpPresenter<V extends MvpView> extends LifeCyclePresenter {

    /**
     * Set or attach the view to this presenter
     */
    @UiThread
    void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    @UiThread
    void detachView();

}
