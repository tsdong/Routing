package com.codeit.priorityrouting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private static final LatLng LOWER_MANHATTAN = new LatLng(40.722543, -73.998585);
    private static final LatLng TIMES_SQUARE = new LatLng(40.7577, -73.9857);
    private static final LatLng BROOKLYN_BRIDGE = new LatLng(40.7057, -73.9964);
    private static final LatLng Oakland_University = new LatLng(42.672979,-83.215783);

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    final String TAG = "PathGoogleMapActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
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
        setContentView(R.layout.activity_maps);
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap = fm.getMap();

        String userInput = getIntent().getExtras().getString("addr");

        Toast.makeText(this, userInput, Toast.LENGTH_LONG).show();

        MarkerOptions options = new MarkerOptions();
        options.position(LOWER_MANHATTAN);
        options.position(BROOKLYN_BRIDGE);
        options.position(TIMES_SQUARE);
        mMap.addMarker(options);
        String url = getMapsApiDirectionsUrl();
        ReadTask downloadTask = new ReadTask();
        downloadTask.execute(url);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Oakland_University,13));
        addMarkers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call  once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                addMarkers();
            }
        }
    }

    /**
     * this sets up the URL request using the given coordinates
     *
     */
    private String getMapsApiDirectionsUrl(){



        //String waypoints = LOWER_MANHATTAN.latitude + "," + LOWER_MANHATTAN.longitude + "|" + BROOKLYN_BRIDGE.latitude +
               // "," + BROOKLYN_BRIDGE.longitude + "|" + TIMES_SQUARE.latitude + "," + TIMES_SQUARE.longitude;

        String userInput = getIntent().getExtras().getString("addr");
        String waypoints = userInput;


                String params = "waypoints=optimize:true|" + waypoints;
        String origin = "Oakland+University";
//        String destination = "2100+Woodward+Ave,+Detroit,+MI+48210";
        String destination = "Pontiac";
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?origin=" + origin + "&destination=" + destination + "&" + params;

        return url;
    }

    /**
     * add markers to map if map is up
     */
    private void addMarkers(){
        if (mMap != null) {
            mMap.addMarker(new MarkerOptions().position(BROOKLYN_BRIDGE).title("First Point"));
            mMap.addMarker(new MarkerOptions().position(LOWER_MANHATTAN).title("Second Point"));
            mMap.addMarker(new MarkerOptions().position(TIMES_SQUARE).title("Third Point"));
        }
    }



    private class ReadTask extends AsyncTask<String, Void, String> {

        /**
         * set up an http connection
         */
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                HttpConnection http = new HttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }


        /**
         * call parsertask
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }


    private class ParserTask extends AsyncTask<String,Integer,List<List<HashMap<String, String>>>> {


        /**
         *should be creating paths
         * @param jsonData
         * @return
         */
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.i("returning routes:", routes.toString());
            return routes;

        }

        /**
         * should be drawing the paths
         * @param routes
         */
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(8);
                polyLineOptions.color(Color.BLUE);
            }

            mMap.addPolyline(polyLineOptions);
        }
    }
}
