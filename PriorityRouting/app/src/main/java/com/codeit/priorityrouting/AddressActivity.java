package com.codeit.priorityrouting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


public class AddressActivity extends ActionBarActivity {

    Button btnAdd;
    ArrayList<String> addArray = new ArrayList<String>();
    EditText et;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        et = (EditText) findViewById(R.id.et_place);
        lv = (ListView) findViewById(R.id.addressListView);
        btnAdd = (Button) findViewById(R.id.btn_add);
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
                    ((TextView) findViewById(R.id.addEntry)).setText("");
                }
            }
        });

        final Button mapButton = (Button) findViewById(R.id.btn_route);
        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(AddressActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

        //back button on the address entry page.
        final Button backBtn = (Button) findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AddressActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_address, menu);
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
