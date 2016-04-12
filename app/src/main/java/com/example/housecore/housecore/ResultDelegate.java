package com.example.housecore.housecore;

import com.paypal.android.MEP.PayPalResultDelegate;

import java.io.Serializable;

public class ResultDelegate implements PayPalResultDelegate, Serializable {
    private static final long serialVersionUID = 10001L;
    private static String uuid;

    public ResultDelegate(){
        super();
    }
    public ResultDelegate(String uuid){
        super();
        this.uuid = uuid;
    }

    public String getUUID(){
        return this.uuid;
    }

    public void onPaymentSucceeded(String payKey, String paymentStatus) {
        // TODO: update database marking payment as successful
        PayActivity.resultTitle = "SUCCESS";
        PayActivity.resultInfo = "You have successfully completed your transaction.";
        PayActivity.resultExtra = "Key: " + payKey;
    }


    public void onPaymentFailed(String paymentStatus, String correlationID,
                                String payKey, String errorID, String errorMessage) {
        // TODO: update database marking payment as failed
        PayActivity.resultTitle = "FAILURE";
        PayActivity.resultInfo = errorMessage;
        PayActivity.resultExtra = "Error ID: " + errorID + "\nCorrelation ID: "
                + correlationID + "\nPay Key: " + payKey;
    }


    public void onPaymentCanceled(String paymentStatus) {
        // TODO: update database marking payment as failed
        PayActivity.resultTitle = "CANCELED";
        PayActivity.resultInfo = "The transaction has been cancelled.";
        PayActivity.resultExtra = "";
    }
}