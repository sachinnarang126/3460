package library.basecontroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import library.mvp.BasePresenter;
import rx.Subscription;

/**
 * @author Sachin Narang
 */

public abstract class AppCompatFragment<T extends BasePresenter> extends Fragment {

    private T presenter;
    /**
     * holds the executing or executed service call instances
     */
//    private HashMap<String, Call> mServiceCallsMap;
    private HashMap<String, Subscription> mRxSubscriberMap;

    /**
     * Empty constructor to initialize the service map
     */
    public AppCompatFragment() {
//        mServiceCallsMap = new HashMap<>();
        mRxSubscriberMap = new HashMap<>();
    }

    public T getPresenter() {
        return presenter;
    }

    abstract protected T createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        /*if (mServiceCallsMap != null) {
            cancelAllServiceCalls(new ArrayList<>(mServiceCallsMap.values()));
            mServiceCallsMap = null;
        }*/

        if (mRxSubscriberMap != null) {
            unSubscribeAllRxSubscriber(new ArrayList<>(mRxSubscriberMap.values()));
            mRxSubscriberMap = null;
        }
    }

    /**
     * this function will can cel all the service which can have an asynchronous response from server
     * <p>
     * //* @param serviceCallList pass the list of service you want to cancel
     */
    /*private void cancelAllServiceCalls(List<Call> serviceCallList) {
        for (Call call : serviceCallList)
            if (call != null) call.cancel();
    }*/
    private void unSubscribeAllRxSubscriber(List<Subscription> subscriptionList) {
        for (Subscription subscription : subscriptionList)
            try {
                if (!subscription.isUnsubscribed()) subscription.unsubscribe();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
    }

    final public void unSubscribeFromSubscriptionIfSubscribed(String key) {
        if (mRxSubscriberMap.containsKey(key) && !mRxSubscriberMap.get(key).isUnsubscribed()) {
            mRxSubscriberMap.get(key).unsubscribe();
            mRxSubscriberMap.remove(key);
        }
    }

    /**
     * returns the service call object from service map
     *
     * @param key key value of the service call (Basically the url)
     * @param <T> Generic type of the service call
     * @return Returns the Generic type if exists otherwise null
     */
    /*final public <T> Call<T> getServiceCallIfExist(String key) {
        if (mServiceCallsMap != null && mServiceCallsMap.containsKey(key))
            return mServiceCallsMap.get(key).clone();
        else
            return null;
    }*/

    /**
     * create Call Service and put it in Service Map
     * <p>
     * //* @param call Call Service object
     *
     * @param key key value of Call Service (Basically URL)
     * @param <T> Generic type of Call Service
     */
    /*final public <T> void putServiceCallInServiceMap(Call<T> call, String key) {
        mServiceCallsMap.put(key, call);
    }*/
    final public <T> void putSubscriberInMap(Subscription subscription, String key) {
        mRxSubscriberMap.put(key, subscription);
    }

    /**
     * checks whether call service exists in service map or not
     *
     * @param key key of call service (Basicallly URL)
     * @return true or false
     */
    /*final public boolean isServiceCallExist(String key) {
        return mServiceCallsMap.containsKey(key);
    }*/
}
