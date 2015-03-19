package com.codeit.priorityrouting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codeit.priorityrouting.DBSupport.Select.SelectToVerifyLogIn;


public class LoginActivity extends ActionBarActivity {

    protected EditText mUserPassword;
    protected EditText mUserEmail;

    protected Button mLoginButton;

    public String emailAddress;
    public String userPassword;

    public void selectObjectReturn(String res){

        verifyUser(res);

    }


    public void verifyUser(String response){

        if(response.equalsIgnoreCase("[]")){
            //insert into data base

            Toast.makeText(LoginActivity.this, "You have entered the wrong username.Please enter the correct username.", Toast.LENGTH_LONG).show();

        }
        else if(response.contains(userPassword)){
            Toast.makeText(LoginActivity.this, "You have entered the correct password and username. Logging In......", Toast.LENGTH_LONG).show();
            Intent goToHomePage = new Intent(LoginActivity.this.getApplicationContext(), HomeActivity.class);
            startActivity(goToHomePage);
        }
        else{
            Toast.makeText(LoginActivity.this, "You have entered the incorrect password.", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mUserEmail = (EditText)findViewById(R.id.usrName);
        mUserPassword = (EditText)findViewById(R.id.password);
        mLoginButton = (Button)findViewById(R.id.sign_in_button);

        //Login button
       /* final Button loginBtn = (Button) findViewById(R.id.sign_in_button);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
*/
        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //get the username, password and email and convert them to string

                String email = mUserEmail.getText().toString().trim();
                emailAddress=email;
                String password = mUserPassword.getText().toString().trim();
                userPassword=password;


                SelectToVerifyLogIn selectToVerifyLogIn = new SelectToVerifyLogIn();

                selectToVerifyLogIn.execute(email);
                selectToVerifyLogIn.loginActivity = LoginActivity.this;



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