package com.codeit.priorityrouting;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
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

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_maps);

        //Login button
        final Button loginBtn = (Button) findViewById(R.id.sign_in_button);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, AddressActivity.class);
                startActivity(i);
            }
        });

        //Back button on the map page
        final Button backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, AddressActivity.class);
                startActivity(i);
            }
        });

        //Logout button on the map page
        final Button logoutBtn = (Button) findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        // Register Button
        final Button regBtn = (Button) findViewById(R.id.register_button);
        regBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}