package com.example.housecore.housecore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.PACKAGE_USAGE_STATS;
import static android.Manifest.permission.READ_CONTACTS;

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

    //logs in user
    public void login(View view) {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        //if form is valid, proceed to login
        if(isFormValid()){

        }
    }

    //switches to the create account activity
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

