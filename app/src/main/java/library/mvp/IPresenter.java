package library.mvp;

import android.content.Context;
import android.support.annotation.UiThread;

/**
 * The base interface for each mvp presenter.
 *
 * @author Sachin Narang
 */

interface IPresenter<V extends BaseView> {

    /**
     * Set or attach the view to this presenter
     */
    @UiThread
    void attachView(V view, Context context);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    @UiThread
    void detachView();

}
