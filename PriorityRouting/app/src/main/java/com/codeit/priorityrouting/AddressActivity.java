package com.codeit.priorityrouting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AddressActivity extends ActionBarActivity {

    Button btnAdd;
    ArrayList<String> addArray = new ArrayList<String>();
    EditText et;
    ListView lv;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_address, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
                //loggedIn = false;
                //authentication = false;
                Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                openSettings();
                return true;
            //default:
            //return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSettings(){
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        et = (EditText) findViewById(R.id.get_place);
        lv = (ListView) findViewById(R.id.addressListView);


        ////////////
        //Dialog Box
        ////////////
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter an Address");
        builder
                .setMessage("Enter an address into the 'Enter Address' field and do not click 'Add'. Once address has been entered, click 'Map It'.")
                .setCancelable(false)
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        /////////
        //Create alert dialog
        /////////
        AlertDialog alertDialog = builder.create();

        /////////
        //Show dialog
        /////////
        alertDialog.show();

        /////////
        //Buttons
        /////////
        btnAdd = (Button) findViewById(R.id.btn_add);
        final Button mapButton = (Button) findViewById(R.id.btn_route);
        final Button backBtn = (Button) findViewById(R.id.btn_back);

        //////////////////
        //Button Functions
        //////////////////

        //Add address to ListView
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInput = et.getText().toString();

                if(addArray.contains(getInput)){
                    Toast.makeText(getBaseContext(), "Address already exists list", Toast.LENGTH_LONG);
                }
                else if(getInput == null || getInput.trim().equals("")){
                    Toast.makeText(getBaseContext(), "Address entry is empty.", Toast.LENGTH_LONG);
                }
                else{
                    addArray.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_list_item_1, addArray);
                    lv.setAdapter(adapter);
                    ((EditText) findViewById(R.id.get_place)).setText("");
                }
            }
        });

        //Navigate to map page
        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                et = (EditText) findViewById(R.id.get_place);
                String location = et.getText().toString();
                location = location.replace(" ", "+");

                Intent i = new Intent(AddressActivity.this, MapsActivity.class);
                i.putExtra("addr", location);
                startActivity(i);

            }
        });

        //Navigate back to Home Page
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AddressActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

    }
}
