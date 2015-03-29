package com.codeit.priorityrouting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Address;
import android.net.Uri;
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
import android.widget.AdapterView;
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
    Geocoder geocoder;
    ArrayList<String> addArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    EditText et;
    ListView lv;
    String toBePassed = "";


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

/*        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
*/

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
                    if(toBePassed.equals("")) {
                        toBePassed = toBePassed + getInput;
                    }
                    else {
                        toBePassed = toBePassed + "|" + getInput;
                    }
                    addArray.add(getInput);
                    adapter = new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_list_item_1, addArray);
                    lv.setAdapter(adapter);
                    ((EditText) findViewById(R.id.get_place)).setText("");
                }
            }
        });


        /////////
        //Remove Item
        /////////
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItemFromList(position);
                return true;
            }
        });


        //Navigate to map page
        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                et = (EditText) findViewById(R.id.get_place);
                String destination = et.getText().toString();
                destination = destination.replace(" ", "+");

                //et = (EditText) findViewById(R.id.get_place);
                //String location = et.getText().toString();
                //location = location.replace(" ", "+");
/*
                lv = (ListView) findViewById(R.id.addressListView);
                String locCoord = lv.toString();
                String coords = null;
                List<Address> addrs = null;
                try {
                    addrs = geocoder.getFromLocationName(locCoord, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addrs != null && addrs.size() > 0) {
                    double lat = (double) (addrs.get(0).getLatitude() * 1000000);
                    double lng = (double) (addrs.get(0).getLongitude() * 1000000);

                    coords = String.valueOf((lat + "," + lng));
                }
*/

                toBePassed = toBePassed.replace(" ", "+");

                Intent i = new Intent(AddressActivity.this, MapsActivity.class);
                i.putExtra("desto", destination);
                i.putExtra("addr", toBePassed);
//                i.putExtra("latlng", coords);
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

    //method to remove list item
    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(AddressActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to delete this address?");
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addArray.remove(deletePosition);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    //Geocode address

}