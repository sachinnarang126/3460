package library.mvp;

import android.support.annotation.UiThread;

/**
 * @author Sachin Narang
 */

public interface LceView<M> extends BaseView {
    /**
     * Display a loading view while loading data in background.
     * <b>The loading view must have the id = R.id.loadingView</b>
     *
     * @param pullToRefresh true, if pull-to-refresh has been invoked loading.
     */
    @UiThread
    public void showLoading(boolean pullToRefresh);

    /**
     * Show the content view.
     * <p>
     * <b>The content view must have the id = R.id.contentView</b>
     */
    @UiThread
    public void showContent();

    /**
     * Show the error view.
     * <b>The error view must be a TextView with the id = R.id.errorView</b>
     *
     * @param e             The Throwable that has caused this error
     * @param pullToRefresh true, if the exception was thrown during pull-to-refresh, otherwise
     *                      false.
     */
    @UiThread
    public void showError(Throwable e, boolean pullToRefresh);

    /**
     * The data that should be displayed with {@link #showContent()}
     */
    @UiThread
    public void setData(M data);

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
    public void loadData(boolean pullToRefresh);
}
