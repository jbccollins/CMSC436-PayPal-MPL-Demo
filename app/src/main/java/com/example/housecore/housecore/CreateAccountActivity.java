package com.example.housecore.housecore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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

import static android.Manifest.permission.READ_CONTACTS;

/*
Allows user to create an account
 */
public class CreateAccountActivity extends AppCompatActivity {

    EditText email, name, password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    /*
    Registers the user and then leads the user to the groups page.
    Method is evoked when the register button is clicked.
    */
    public void register(View view) {
        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);

        //if form is valid, create account and then login TODO: open up groups page activiy
        if(isFormValid()){

        }

    }

    //checks if all contents of the form are filled out correctly
    private boolean isFormValid(){

        boolean valid = true;

        //form handling
        if(!isEmailValid(email.getText().toString())) {
            email.setError(getResources().getString(R.string.error_invalid_email));
            valid = false;
        }
        if(email.getText().toString().trim().length() == 0) {
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
        if(name.getText().toString().trim().length() == 0) {
            name.setError(getResources().getString(R.string.error_field_required));
            valid = false;
        }
        if(!confirm_password.getText().toString().equals(password.getText().toString())) {
            confirm_password.setError(getResources().getString(R.string.mismatching_passwords));
            valid = false;
        }
        if(confirm_password.getText().toString().trim().length() == 0) {
            confirm_password.setError(getResources().getString(R.string.error_field_required));
            valid = false;
        }
        return valid;
    }

    //checks to see if the email is valid
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    //checks for password validity
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}

