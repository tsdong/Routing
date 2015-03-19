package com.codeit.priorityrouting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codeit.priorityrouting.DBSupport.Insert.InsertObject;
import com.codeit.priorityrouting.DBSupport.Insert.InsertServiceCallRunner;
import com.codeit.priorityrouting.DBSupport.Select.SelectServiceCallRunner;
import com.google.gson.Gson;


public class RegistrationActivity extends ActionBarActivity {

    protected EditText mUserEmail;
    protected EditText mUserPassword;
    protected EditText mUserConfirmPassword;

    protected Button mSubmitButton;
    public String output;

    public String confirmPassword;
    public String email1;
    public String password1;

    Gson gson = new Gson();


    public void selectObjectReturn(String res){

        RegistrationActivity.this.output = res;
        verifyUser(res);

    }

    public void insertObjectReturn(String insertres){

        System.out.println("insert return: " + insertres);

        Toast.makeText(RegistrationActivity.this, "Successfully. Please enter userName and password to log in", Toast.LENGTH_LONG).show();
        Intent goToHomePage = new Intent(RegistrationActivity.this.getApplicationContext(), LoginActivity.class);
        startActivity(goToHomePage);
    }

    public void verifyUser(String response){

        if(response.equalsIgnoreCase("[]")){
            //insert into data base
            System.out.println("I am going to insert........");

            InsertObject insertObject = new InsertObject();
            insertObject.setEmail(email1);
            insertObject.setPassword(password1);
            insertObject.setConfirmPassword(confirmPassword);

            String insertJson = gson.toJson(insertObject);

            InsertServiceCallRunner insertServiceCallRunner = new InsertServiceCallRunner();

            insertServiceCallRunner.execute(insertJson);
            insertServiceCallRunner.registrationActivity = RegistrationActivity.this;

        }
        else{
            Toast.makeText(RegistrationActivity.this, "you are registered. Please enter the correct userName and password to log in", Toast.LENGTH_LONG).show();

            Intent goToHomePage = new Intent(RegistrationActivity.this.getApplicationContext(), LoginActivity.class);
            startActivity(goToHomePage);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUserEmail = (EditText)findViewById(R.id.email);
        mUserPassword = (EditText)findViewById(R.id.password);
        mUserConfirmPassword = (EditText)findViewById(R.id.confPassword);
        mSubmitButton = (Button) findViewById(R.id.email_sign_in_button);


//        final Button btnSubmit = (Button) findViewById(R.id.email_sign_in_button);
        /*mSubmitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
*/
        mSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //get the username, password and email and convert them to string



                String email = mUserEmail.getText().toString().trim();
                email1=email;

                String password = mUserPassword.getText().toString().trim();
                password1=password;

                String confirmPass = mUserConfirmPassword.getText().toString().trim();
                confirmPassword=confirmPass;

                //verify password and confirmPassword are matched
                if(password.equals(confirmPass)){
                    SelectServiceCallRunner selectServiceCallRunner = new SelectServiceCallRunner();

                    selectServiceCallRunner.execute(email);
                    selectServiceCallRunner.registrationActivity = RegistrationActivity.this;
                }
                else{
                    Toast.makeText(RegistrationActivity.this, "Password don't match. Please make sure the passwords are matched", Toast.LENGTH_LONG).show();
                }






            }
        });

        final Button btnCancel = (Button) findViewById(R.id.email_sign_in_button_2);
        btnCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
