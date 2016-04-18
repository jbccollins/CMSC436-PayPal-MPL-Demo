package com.example.housecore.housecore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /*
    Leads the user to the groups page if user has valid credentials.
    Method is evoked when the Login button is clicked.
    */
    public void login(View view) {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        //if form is valid, proceed to login
        if(isFormValid()){
            /*
            Intent intent = new Intent(this, MyGroups.class);
            startActivity(intent);
            */

                // This is how you should call the PayActivity class.
                Intent payIntent = new Intent(getBaseContext(), PayActivity.class);
                payIntent.putExtra("PAYMENT_AMOUNT", "5");
                payIntent.putExtra("PAYMENT_ID", "sdffsg-fjeo33-lkjn2-on0h7n");
                payIntent.putExtra("RECIPIENT_EMAIL", "housecore_testtwo@gmail.com");
                startActivity(payIntent);

        }
    }

    //Switches to the create account activity
    public void createAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    //checks if the login form is valid
    private boolean isFormValid(){

        boolean valid = true;
        //form handling
        if(!isEmailValid(email.getText().toString())) {
            email.setError(getResources().getString(R.string.error_invalid_email));
            valid = false;
        }
        if(email.getText().toString().trim().length() == 0 ) {
            email.setError(getResources().getString(R.string.error_field_required));
            valid = false;
        }
        if(!isPasswordValid(password.getText().toString())) {
            password.setError(getResources().getString(R.string.error_invalid_password));
            valid = false;
        }
        if(password.getText().toString().trim().length() == 0) {
            password.setError(getResources().getString(R.string.error_field_required));
            valid = false;
        }
        return valid;
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}

