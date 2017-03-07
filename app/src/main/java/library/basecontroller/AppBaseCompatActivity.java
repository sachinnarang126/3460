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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
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
     * In child Activity you must provide presenter implementation to this,
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
        if (presenter != null) presenter.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (presenter != null) presenter.onCreateOptionsMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (presenter != null) presenter.onPrepareOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (presenter != null) presenter.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
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

    public void hideSoftKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdRequest getAddRequest() {
        return new AdRequest.Builder().
                /*.addTestDevice("9210683FFFBDE1953CE613AB2FDE46E5").
                        addTestDevice("F56162DD974939BBF71A8D3E8CC8A44A").
                        addTestDevice("1FBF7D7CF19C0C11158AF44FDA595121").*/
                        addTestDevice("F58DA099F52C8D53E4DD635D0C5EB709").
                        addTestDevice("D083D5AB51804F754591182D7F09C824").
                        build();
    }

}
