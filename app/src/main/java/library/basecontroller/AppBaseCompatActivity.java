package library.basecontroller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.util.Constant;
import com.tech.quiz.view.activity.SplashActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import library.mvp.MvpBasePresenter;
import rx.Subscription;


public abstract class AppBaseCompatActivity<T extends MvpBasePresenter> extends AppCompatActivity {

    private T presenter;
    /**
     * holds the executing or executed service call instances
     */

//    private HashMap<String, Call> mServiceCallsMap;
    private HashMap<String, Subscription> mRxSubscriberMap;

    public T getPresenter() {
        return presenter;
    }

    abstract protected T createPresenter();

    /**
     * this function will cancel all the service which can have an asynchronous response from server
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mServiceCallsMap = new HashMap<>();
        mRxSubscriberMap = new HashMap<>();
        presenter = createPresenter();
        if (presenter != null)
            presenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null)
            presenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null)
            presenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
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
     * display the toast
     *
     * @param text text to display
     */

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * checks whether internet is enabled or not
     *
     * @return true if enabled otherwise false
     */

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                    && PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
            } else
                return false;
        } else {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }
    }

    /**
     * open the play store for Rating
     *
     * @param pack app package
     */

    public void openPlayStoreForRating(String pack) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pack)));
    }

    /**
     * returns the service call object from service map, you can not override this method.
     *
     * @param key key value of the service call (Basically the url)
     * @param <V> Generic type of the service call
     * @return Returns the Generic type if exists otherwise null
     */

    /*final public <V> Call<V> getServiceCallIfExist(String key) {
        if (mServiceCallsMap != null && mServiceCallsMap.containsKey(key))
            return mServiceCallsMap.get(key).clone();
        else
            return null;
    }*/

    /**
     * create Call Service and put it in Service Map, you can not override this method.
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
     * checks whether call service exists in service map or not, you can not override this method.
     *
     * @param key key of call service (Basically URL)
     * @return true or false
     */

    /*final public boolean isServiceCallExist(String key) {
        return mServiceCallsMap.containsKey(key);
    }*/
    final public void unSubscribeFromSubscriptionIfSubscribed(String key) {
        if (mRxSubscriberMap.containsKey(key) && !mRxSubscriberMap.get(key).isUnsubscribed()) {
            mRxSubscriberMap.get(key).unsubscribe();
            mRxSubscriberMap.remove(key);
        }
    }

    public boolean isSubscribedUser() {
        return DataHolder.getInstance().getPreferences(this).getBoolean(Constant.IS_SUBSCRIBED_USER, false);
    }

    public void restartApp() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
