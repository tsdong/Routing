package com.codeit.priorityrouting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
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

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng Origin = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());

        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap = fm.getMap();

//        setUpMapIfNeeded();
        String userInput = getIntent().getExtras().getString("addr");
//        String addrCoords = getIntent().getExtras().getString("latlng");

        Toast.makeText(this, userInput.replace("+"," "), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, addrCoords, Toast.LENGTH_LONG).show();


        String url = getMapsApiDirectionsUrl(Origin);
        ReadTask readTask = new ReadTask();
        readTask.execute(url);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Origin,13));
        addMarkers(getIntent().getExtras().getStringArrayList("markers"));
    }
/*
    @Override
    protected void onResume(ArrayList<String> markers) {
        super.onResume();
        setUpMapIfNeeded(markers);
    }
*/
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
/*
    private void setUpMapIfNeeded(ArrayList<String> markers) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //setUpMap();
                addMarkers(markers);
            }
        }
    }
*/
    /**
     * this sets up the URL request using the given coordinates
     *
     */
    private String getMapsApiDirectionsUrl(LatLng Origin){


        String Origin2 = Origin.toString();
        String Origin3 = Origin2.replace(")","");
        String Origin4 = Origin3.replace("lat/lng: (","");


        //String waypoints = LOWER_MANHATTAN.latitude + "," + LOWER_MANHATTAN.longitude + "|" + BROOKLYN_BRIDGE.latitude +
               // "," + BROOKLYN_BRIDGE.longitude + "|" + TIMES_SQUARE.latitude + "," + TIMES_SQUARE.longitude;

        String userInput = getIntent().getExtras().getString("addr");
        String waypoints = userInput;


        String params = "waypoints=optimize:true|" + waypoints;

        String origin = Origin4;
//        String destination = "2100+Woodward+Ave,+Detroit,+MI+48210";
        String destination = getIntent().getExtras().getString("desto");
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?origin=" + origin + "&destination=" + destination + "&" + params;

        return url;
    }

    /**
     * add markers to map if map is up
     */
    private void addMarkers(ArrayList<String> markers){
        if (mMap != null) {

            for(int i=0;i<markers.size();i++){
                DownloadTask downloadTask = new DownloadTask();
                String marker = markers.get(i);
                downloadTask.execute(marker);
            }
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
/*
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));

        //Enable MyLocation layer of Google Map
        mMap.setMyLocationEnabled(true);

        //Get LocationManager object from System Service LOCATION_SERVICES
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        //Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        //Get current location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        //Set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Enable traffic
        mMap.setTrafficEnabled(false);

        //Get Lat of current location
        double latitude = myLocation.getLatitude();

        //Get Lng of current location
        double longitude = myLocation.getLongitude();

        //Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        //Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here"));
    }
*/
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                HttpConnection http2 = new HttpConnection();
                data = http2.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            new ParserTask2().execute(result);
        }
    }

    class ParserTask2 extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            GeocodeJSONParser parser = new GeocodeJSONParser();

            try {
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a an ArrayList */
                places = parser.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            for(int i=0;i<list.size();i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> hmPlace = list.get(i);
                double lat = Double.parseDouble(hmPlace.get("lat"));
                double lng = Double.parseDouble(hmPlace.get("lng"));
                String name = hmPlace.get("formatted_address");
                LatLng latLng = new LatLng(lat, lng);
                markerOptions.position(latLng);
                markerOptions.title(name);
                mMap.addMarker(markerOptions);
            }
        }
    }

}
