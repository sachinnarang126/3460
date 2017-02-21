package library.mvp;

import android.support.annotation.UiThread;

/**
 * @author Sachin Narang
 */

public interface LceView<M> extends BaseView {
    /**
     * Display a loading view while loading data in background.
     * <b>The loading view must have the id = R.id.loadingView</b>
     */
    @UiThread
    void showLoading();

    /**
     * Show the content view.
     * <p>
     * <b>The content view must have the id = R.id.contentView</b>
     */
    @UiThread
    void showContent();

    /**
     * Show the error view.
     * <b>The error view must be a TextView with the id = R.id.errorView</b>
     *
     * @param e The Throwable that has caused this error
     */
    @UiThread
    void showError(Throwable e);

    /**
     * The data that should be displayed with {@link #showContent()}
     */
    @UiThread
    void setData(M data);

    /**
     * Load the data. Typically invokes the presenter method to load the desired data.
     * <p>
     * <b>Should not be called from presenter</b> to prevent infinity loops. The method is declared
     * in
     * the views interface to add support for view state easily.
     * </p>
     *
     * @param pullToRefresh true, if triggered by a pull to refresh. Otherwise false.
     */
    @UiThread
    void loadData(boolean pullToRefresh);
}
