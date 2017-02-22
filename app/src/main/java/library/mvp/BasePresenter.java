package library.mvp;

import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v4.util.ArrayMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * @author Sachin Narang
 */

abstract public class BasePresenter<V extends BaseView, T extends IBaseInterActor> implements IPresenter<V> {

    private WeakReference<Context> contextRef;
    private WeakReference<V> viewRef;
    private T interActor;
    /**
     * holds the executing or executed service call instances
     */
    private Map<String, Subscription> mRxSubscriberMap;

    @UiThread
    @Override
    public void attachView(V view, Context context) {
        viewRef = new WeakReference<>(view);
        contextRef = new WeakReference<>(context);
        interActor = createInterActor();
        mRxSubscriberMap = new ArrayMap<>();
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
        }

        if (contextRef != null) {
            contextRef.clear();
            contextRef = null;
        }

        if (interActor != null)
            interActor = null;

        if (mRxSubscriberMap != null) {
            unSubscribeAllRxSubscriber(new ArrayList<>(mRxSubscriberMap.values()));
            mRxSubscriberMap = null;
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
        if (contextRef != null)
            return contextRef.get();
        return null;
    }

    protected abstract T createInterActor();

    protected T getInterActor() {
        return interActor;
    }

    /**
     * this function will cancel all the service which can have an asynchronous response from server
     */
    private void unSubscribeAllRxSubscriber(List<Subscription> subscriptionList) {
        for (Subscription subscription : subscriptionList)
            try {
                if (!subscription.isUnsubscribed()) subscription.unsubscribe();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
    }

    /**
     * create Call Service and put it in Service Map, you can not override this method.
     *
     * @param call Call Service object
     * @param key  key value of Call Service (Basically URL)
     */
    @SuppressWarnings("ALL")
    final protected void putSubscriberInMap(Subscription subscription, String key) {
        mRxSubscriberMap.put(key, subscription);
    }

    /**
     * checks whether call service exists in service map or not, you can not override this method.
     *
     * @param key key of call service (Basically URL)
     * @return true or false
     */
    @SuppressWarnings("ALL")
    final protected boolean isServiceCallExist(String key) {
        return mRxSubscriberMap.containsKey(key);
    }

    @SuppressWarnings("ALL")
    final protected void unSubscribeFromSubscriptionIfSubscribed(String key) {
        if (mRxSubscriberMap.containsKey(key) && !mRxSubscriberMap.get(key).isUnsubscribed()) {
            mRxSubscriberMap.get(key).unsubscribe();
            mRxSubscriberMap.remove(key);
        }
    }

    /**
     * returns the service call object from service map, you can not override this method.
     *
     * @param key key value of the service call (Basically the url)
     * @return Returns Subscription if exists otherwise null
     */
    @SuppressWarnings("ALL")
    final public Subscription getSubscriptionIfExist(String key) {
        return mRxSubscriberMap.get(key);
    }
}
