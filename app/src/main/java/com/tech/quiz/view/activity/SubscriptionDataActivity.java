package com.tech.quiz.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.tech.R;
import com.tech.quiz.billing.IabHelper;
import com.tech.quiz.billing.IabResult;
import com.tech.quiz.billing.Inventory;
import com.tech.quiz.billing.Purchase;
import com.tech.quiz.dataholder.DataHolder;
import com.tech.quiz.util.Constant;

import library.basecontroller.AppBaseCompatActivity;
import library.mvp.ActivityPresenter;

/**
 * @author Sachin Narang
 */

public class SubscriptionDataActivity extends AppBaseCompatActivity {

    public final static String ITEM_SKU = "ad_free";
    private static final int REQUEST_CODE_GOOGLE_WALLET = 10001;
    //    private String TAG = "com.tech.quiz";
    /*IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase, IabResult result) {
                    Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
                    if (result.isSuccess()) {

                    } else {
                        // handle error
                    }
                }
            };*/
    private IabHelper mHelper;
    private final IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

            // if we were disposed of in the meantime, quit.
            if (mHelper == null)
                return;

            if (purchase != null) {
                SharedPreferences sharedPreferences = DataHolder.getInstance().getPreferences(SubscriptionDataActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Constant.IS_SUBSCRIBED_USER, true);
                editor.putLong(Constant.PURCHASE_TIME, purchase.getPurchaseTime());
                editor.putString(Constant.TOKEN, purchase.getToken());
                editor.putString(Constant.SKU, purchase.getSku());
                editor.apply();
            }
        }
    };
    // Listener that's called when we finish querying the items and subscriptions we own
    private final IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
//                Utils.showToast(SubscriptionDataActivity.this, "Failed to query inventory: " + result);
                finish();
            }

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            Purchase purchase = inventory.getPurchase(ITEM_SKU);

            if (purchase != null) {
                SharedPreferences sharedPreferences = DataHolder.getInstance().getPreferences(SubscriptionDataActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Constant.IS_SUBSCRIBED_USER, true);
                editor.putLong(Constant.PURCHASE_TIME, purchase.getPurchaseTime());
                editor.putString(Constant.TOKEN, purchase.getToken());
                editor.putString(Constant.SKU, purchase.getSku());
                editor.apply();
            } else {
                launchPurchaseFlow();
                // enableSubscribeButton();
            }


            /*mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                    mConsumeFinishedListener);*/
        }
    };

    @Override
    protected ActivityPresenter createPresenter() {
        return null;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_subcription_data);
        super.onCreate(savedInstanceState);
        initiateGoogleWallet();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DataHolder.getInstance() == null) {
            restartApp();
        }
    }

    private void initiateGoogleWallet() {
        // Create the helper, passing it our context and the public key to verify signatures with
        mHelper = new IabHelper(this, Constant.BASE_64);
        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);


        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
//                    Utils.showToast(SubscriptionDataActivity.this, "Problem setting up in-app billing: " + result);
                    finish();
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) {
                    finish();
                    return;
                }


                // IAB is fully set up. Now, let's get an inventory of stuff we own.
//                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    /*public void consumeItem() {
        mHelper.queryInventoryAsync(mGotInventoryListener);
    }*/

    /*boolean verifyDeveloperPayload(Purchase p) {
//        String payload = p.getDeveloperPayload();

        *//*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         *//*

        return true;
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    private void launchPurchaseFlow() {
        /*
         * TODO: for security, generate your payload here for
         * verification. See the comments on
         * verifyDeveloperPayload() for more info. Since this is
         * a SAMPLE, we just use an empty string, but on a
         * production app you should carefully generate this.
						 */
        String payload = "";
        try {
            if (mHelper != null) {
                mHelper.flagEndAsync();
                mHelper.launchPurchaseFlow(this, ITEM_SKU, REQUEST_CODE_GOOGLE_WALLET,
                        mPurchaseFinishedListener, payload);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GOOGLE_WALLET) {
            if (resultCode == Activity.RESULT_OK) {
                if (!mHelper.handleActivityResult(requestCode,
                        resultCode, data)) {
                    super.onActivityResult(requestCode, resultCode, data);
                }
            } else {
                //when google does not provide option to buy, we need to finish the
                //activity otherwise white screen will remaing visible
                finish();
            }
        }
    }
}
