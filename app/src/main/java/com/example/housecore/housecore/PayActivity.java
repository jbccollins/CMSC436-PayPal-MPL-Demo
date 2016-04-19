package com.example.housecore.housecore;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalPayment;

import java.math.BigDecimal;

public class PayActivity extends AppCompatActivity implements OnClickListener {

    // The PayPal environment to be used - can also be ENV_NONE and ENV_LIVE.
    private static final int environment = PayPal.ENV_SANDBOX;
    // The ID of your application that you received from PayPal. This is the default sandbox ID right now.
    private static final String appID = "APP-80W284485P519543T";
    // This is passed in for the startActivityForResult() android function, the value used is up to you.
    private static final int request = 1;
    // The amount that will be paid.
    private static BigDecimal PAYMENT_AMOUNT = null;
    // An ID used to update a database should you so choose to use it.
    private static String PAYMENT_ID = null;
    // The recipient of the payment.
    private static String RECIPIENT_EMAIL = null;

    protected static final int INITIALIZE_SUCCESS = 0;
    protected static final int INITIALIZE_FAILURE = 1;

    // Rather than call findViewByID all the time just store commonly used views.
    TextView labelSimplePayment;
    LinearLayout layoutSimplePayment;
    CheckoutButton launchSimplePayment;
    LinearLayout paypalButtonWrapper;
    Button exitApp;
    TextView title;
    TextView info;
    TextView extra;

    public static String resultTitle;
    public static String resultInfo;
    public static String resultExtra;

    private Handler hRefresh = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INITIALIZE_SUCCESS:
                    setupButtons();
                    break;
                case INITIALIZE_FAILURE:
                    showFailure();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            PAYMENT_AMOUNT = new BigDecimal(extras.getString("PAYMENT_AMOUNT"));
            PAYMENT_ID = extras.getString("PAYMENT_ID");
            RECIPIENT_EMAIL = extras.getString("RECIPIENT_EMAIL");
        } else {
            hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Initialization cannot be done on the main thread.
        Thread libraryInitializationThread = new Thread() {
            @Override
            public void run() {
                initLibrary();
                // The library is initialized so let's create our CheckoutButton and update the UI.
                if (PayPal.getInstance().isLibraryInitialized()) {
                    hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
                } else {
                    hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
                }
            }
        };
        libraryInitializationThread.start();
        // Display the layout and setup commonly used variables.
        setContentView(R.layout.activity_pay);
        layoutSimplePayment = (LinearLayout)findViewById(R.id.layoutSimplePayment);
        labelSimplePayment = (TextView)findViewById(R.id.labelSimplePayment);
        title = (TextView)findViewById(R.id.title);
        info = (TextView)findViewById(R.id.info);
        extra = (TextView)findViewById(R.id.extra);
        exitApp = (Button)findViewById(R.id.exitApp);
        paypalButtonWrapper = (LinearLayout)findViewById(R.id.paypalButtonWrapper);
    }

    public void setupButtons() {
        PayPal pp = PayPal.getInstance();
        // Get the CheckoutButton. There are five different sizes.
        // The text on the button can either be of type TEXT_PAY or TEXT_DONATE.
        launchSimplePayment = pp.getCheckoutButton(this, PayPal.BUTTON_194x37, CheckoutButton.TEXT_PAY);
        // You'll need to have an OnClickListener for the CheckoutButton.
        // For this application, PayActivity implements OnClickListener and we
        // have the onClick() method below.
        launchSimplePayment.setOnClickListener(this);
        // The CheckoutButton is an android LinearLayout so we can add it to our display like any other View.
        paypalButtonWrapper.addView(launchSimplePayment);
        // Show our labels
        labelSimplePayment.setVisibility(View.VISIBLE);
        info.setText("");
        info.setVisibility(View.GONE);
    }

    public void showFailure() {
        title.setText("FAILURE");
        info.setText(R.string.pp_initialization_failure);
        title.setVisibility(View.VISIBLE);
        info.setVisibility(View.VISIBLE);
    }

    private void initLibrary() {
        // Only need to initialize the library once.
        PayPal pp = PayPal.getInstance();
        if (pp == null) {
            // Initialize PayPal
            pp = PayPal.initWithAppID(this, appID, environment);
            // Sender will pay fees. By default this is the receiver.
            // It's free within the U.S. to send money to family and friends when you use only your PayPal
            // balance or bank account, or a combination of their PayPal balance and bank account.
            // So this really shouldn't come into play in our app.
            pp.setFeesPayer(PayPal.FEEPAYER_SENDER);
            // Shipping for Peer to Peer payments is pointless.
            pp.setShippingEnabled(false);
        }
    }

    private PayPalPayment simplePayment() {
        // Create a basic PayPalPayment.
        PayPalPayment payment = new PayPalPayment();
        payment.setSubtotal(PAYMENT_AMOUNT);
        // Sets the currency type for this payment.
        payment.setCurrencyType("USD");
        // Sets the recipient for the payment. This can also be a phone number.
        payment.setRecipient(RECIPIENT_EMAIL);
        // Depending on the payment type the checkout experience can vary slightly.
        // Set this to personal to avoid fee payments.
        payment.setPaymentType(PayPal.PAYMENT_TYPE_PERSONAL);
        return payment;
    }

    @Override
    public void onClick(View v) {
        // Click handler handles both the PayPal button and the exitApp button
        if (v == launchSimplePayment) {
            // Use our helper function to create the simple payment.
            PayPalPayment payment = simplePayment();
            // Use checkout to create our Intent.
            Intent checkoutIntent = PayPal.getInstance().checkout(payment, this, new ResultDelegate(PAYMENT_ID));
            // Use the android's startActivityForResult() and pass in our Intent. This will start the library.
            startActivityForResult(checkoutIntent, request);
        } else if (v == exitApp) {
            Intent in = new Intent();
            in.putExtra("payment", "unpaid");
            // Setting the Requestcode 1.
            setResult(1, in);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != request) {
            return;
        }
        if (PayActivity.resultTitle == "SUCCESS") {
            Intent in = new Intent();
            in.putExtra("payment", "paid");
            setResult(22, in);

        } else if (PayActivity.resultTitle == "FAILURE") {
            Intent in = new Intent();
            in.putExtra("payment", "unpaid");
            setResult(22, in);
        } else if (PayActivity.resultTitle == "CANCELED") {
            Intent in = new Intent();
            in.putExtra("payment", "unpaid");
            setResult(22, in);
        }

        launchSimplePayment.updateButton();

        title.setText(resultTitle);
        title.setVisibility(View.VISIBLE);
        info.setText(resultInfo);
        info.setVisibility(View.VISIBLE);
        extra.setText(resultExtra);
        extra.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent in = new Intent();
            in.putExtra("payment", "unpaid");
            setResult(1, in);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
