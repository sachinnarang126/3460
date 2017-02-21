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

import library.mvp.ActivityPresenter;

/**
 * @author Sachin Narang
 */

public abstract class AppBaseCompatActivity<T extends ActivityPresenter> extends AppCompatActivity {

    private T presenter;

    /**
     * @return return the presenter
     */
    public T getPresenter() {
        return presenter;
    }

    /**
     * In child fragment you must provide presenter implementation to this,
     * otherwise it will give a null pointer exception
     *
     * @return return the presenterImp instance
     */
    abstract protected T createPresenter();

    /**
     * initialized the ui component
     */
    abstract protected void initUI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        initUI();
        if (presenter != null) presenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) presenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) presenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
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
