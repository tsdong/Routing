package com.codeit.priorityrouting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class AddressActivity extends ActionBarActivity {
/*******
    ArrayAdapter<String> m_adapter;
    ArrayList<String> m_listItems = new ArrayList<String>();

    Button btnAdd;
    EditText et;
    TextView tv;
    ListView lv;
*******/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

/***************
        btnAdd = (Button) findViewById(R.id.btn_add);
        et = (EditText) findViewById(R.id.et_place);
        lv = (ListView) findViewById(R.id.addressListView);

        m_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, m_listItems);
        lv.setAdapter(m_adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String input = et.getText().toString();
                if(null != input && input.length() >0) {
                    m_listItems.add(input);
                    m_adapter.notifyDataSetChanged();
                }
            }
        });
**************/

        final Button mapButton = (Button) findViewById(R.id.btn_route);
        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(AddressActivity.this, MapsActivity.class);
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
