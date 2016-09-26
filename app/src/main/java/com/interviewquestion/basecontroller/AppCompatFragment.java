package com.interviewquestion.basecontroller;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class AppCompatFragment extends Fragment {

    /**
     * holds the executing or executed service call instances
     */
    private HashMap<String, Call> mServiceCallsMap;

    /**
     * Empty constructor to initialize the service map
     */
    public AppCompatFragment() {
        mServiceCallsMap = new HashMap<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("--------->>>Destroying All services associated with this fragment<<<<--------");
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
        if (mServiceCallsMap.containsKey(key))
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
