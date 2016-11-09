package library.basecontroller;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import library.mvp.MvpBasePresenter;
import retrofit2.Call;

public abstract class PreferenceCompatFragment<T extends MvpBasePresenter> extends PreferenceFragment {

    private T presenter;
    /**
     * holds the executing or executed service call instances
     */
    private HashMap<String, Call> mServiceCallsMap;

    /**
     * Empty constructor to initialize the service map
     */
    public PreferenceCompatFragment() {
        mServiceCallsMap = new HashMap<>();
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
        cancelAllServiceCalls(new ArrayList<>(mServiceCallsMap.values()));
        mServiceCallsMap = null;
    }

    /**
     * this function will cancel all the service which can have an asynchronous response from server
     *
     * @param serviceCallList pass the list of service you want to cancel
     */
    private void cancelAllServiceCalls(List<Call> serviceCallList) {
        for (Call call : serviceCallList)
            if (call != null) call.cancel();
    }

    /**
     * returns the service call object from service map
     *
     * @param key key value of the service call (Basically the url)
     * @param <T> Generic type of the service call
     * @return Returns the Generic type if exists otherwise null
     */
    final public <T> Call<T> getServiceCallIfExist(String key) {
        if (mServiceCallsMap != null && mServiceCallsMap.containsKey(key))
            return mServiceCallsMap.get(key).clone();
        else
            return null;
    }

    /**
     * create Call Service and put it in Service Map
     *
     * @param call Call Service object
     * @param key  key value of Call Service (Basically URL)
     * @param <T>  Generic type of Call Service
     */
    final public <T> void putServiceCallInServiceMap(Call<T> call, String key) {
        mServiceCallsMap.put(key, call);
    }

    /**
     * checks whether call service exists in service map or not
     *
     * @param key key of call service (Basicallly URL)
     * @return true or false
     */
    final public boolean isServiceCallExist(String key) {
        return mServiceCallsMap.containsKey(key);
    }
}
