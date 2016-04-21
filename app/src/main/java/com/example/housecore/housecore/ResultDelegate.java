package com.example.housecore.housecore;

import com.paypal.android.MEP.PayPalResultDelegate;

import java.io.Serializable;

public class ResultDelegate implements PayPalResultDelegate, Serializable {
    private static String paymentID;

    public ResultDelegate(){
        super();
    }
    public ResultDelegate(String paymentID){
        super();
        this.paymentID = paymentID;
    }

    public String getPaymentID(){
        return this.paymentID;
    }

    public void onPaymentSucceeded(String payKey, String paymentStatus) {
        // TODO: update database marking payment as successful using paymentID
        PayActivity.resultTitle = "SUCCESS";
        PayActivity.resultInfo = "You have successfully completed your transaction.";
        PayActivity.resultExtra = "Key: " + payKey;
    }


    public void onPaymentFailed(String paymentStatus, String correlationID,
                                String payKey, String errorID, String errorMessage) {
        // TODO: update database marking payment as failed using paymentID
        PayActivity.resultTitle = "FAILURE";
        PayActivity.resultInfo = errorMessage;
        PayActivity.resultExtra = "Error ID: " + errorID + "\nCorrelation ID: "
                + correlationID + "\nPay Key: " + payKey;
    }


    public void onPaymentCanceled(String paymentStatus) {
        // TODO: update database marking payment as failed using paymentID
        PayActivity.resultTitle = "CANCELED";
        PayActivity.resultInfo = "The transaction has been cancelled.";
        PayActivity.resultExtra = "";
    }
}