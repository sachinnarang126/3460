package library.mvp;

import android.content.Context;
import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

/**
 * @author Sachin Narang
 */

abstract public class BasePresenter<V extends BaseView> implements IPresenter<V> {

    private Context context;
    private WeakReference<V> viewRef;

    @UiThread
    @Override
    public void attachView(V view, Context context) {
        viewRef = new WeakReference<>(view);
        this.context = context;
    }

    /**
     * Get the attached view. You should always call {@link #isViewAttached()} to check if the view
     * is
     * attached to avoid NullPointerExceptions.
     *
     * @return <code>null</code>, if view is not attached, otherwise the concrete view instance
     */
    @UiThread
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }


    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
            context = null;
        }
    }

    /**
     * Checks if a view is attached to this presenter. You should always call this method before
     * calling {@link #getView()} to get the view instance.
     */
    @UiThread
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    protected Context getContext() {
        return context;
    }
}
