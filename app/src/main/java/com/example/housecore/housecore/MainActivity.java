package com.example.housecore.housecore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by jamescollins on 4/18/16.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v){
        String email;
        String amount;
        EditText recipientEmail = (EditText)findViewById(R.id.enterRecipientEmail);
        EditText recipientAmount = (EditText)findViewById(R.id.enterAmount);
        email = recipientEmail.getText().toString();
        amount = recipientAmount.getText().toString();
        Log.v("EMAIL", email);
        Log.v("AMOUNT", amount);
        if(email.equals("")){
            recipientEmail.setError(getResources().getString(R.string.error_field_required));
            return;
        }
        if(!amount.matches(".*\\d+.*")){
            recipientAmount.setError(getResources().getString(R.string.error_field_required));
            return;
        }
        // This is how you should call the PayActivity class.
        Intent payIntent = new Intent(getBaseContext(), PayActivity.class);
        payIntent.putExtra("PAYMENT_AMOUNT", amount);
        payIntent.putExtra("PAYMENT_ID", "sdffsg-fjeo33-lkjn2-on0h7n");
        payIntent.putExtra("RECIPIENT_EMAIL", email);
        startActivity(payIntent);
    }
}
