package canvas.manipal.com.mygmapapril;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;




// / for mac key generation
//    keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android


// https://developers.google.com/maps/documentation/urls/android-intents#search_for_a_location


// video
// https://www.youtube.com/watch?v=qpkcvUlc7ms

// http://stackoverflow.com/questions/32393134/getting-results-of-nearby-places-from-users-location-using-google-maps-api-in-a

// KEY AIzaSyDNHQgjEks3zoW7m6FMioN_ToNgYcU5kKM

// finding places stack over flow
// http://stackoverflow.com/questions/30161395/im-trying-to-search-nearby-places-such-as-banks-restaurants-atms-inside-the-d

// parse the json
// https://maps.googleapis.com/maps/api/place/search/xml?location=9.934866,76.267235&radius=50000&types=country%7Cairport%7Camusement_park%7Cbank%7Cbook_store%7Cbus_station%7Ccafe%7Ccar_rental%7Ccar_repair%7Cchurch%7Cdoctor%7Cfire_station%7Cfood%7Chindu_temple%7Chospital%7Clawyer%7Clibrary%7Cmosque%7Cmuseum%7Cpark%7Cparking%7Cpharmacy%7Cpolice%7Cpost_office%7Crestaurant%7Cschool%7Ctrain_station%7Czoo&sensor=true&key=your_API_Key
// with api key attached
// https://maps.googleapis.com/maps/api/place/search/xml?location=9.934866,76.267235&radius=50000&types=country%7Cairport%7Camusement_park%7Cbank%7Cbook_store%7Cbus_station%7Ccafe%7Ccar_rental%7Ccar_repair%7Cchurch%7Cdoctor%7Cfire_station%7Cfood%7Chindu_temple%7Chospital%7Clawyer%7Clibrary%7Cmosque%7Cmuseum%7Cpark%7Cparking%7Cpharmacy%7Cpolice%7Cpost_office%7Crestaurant%7Cschool%7Ctrain_station%7Czoo&sensor=true&key=AIzaSyDNHQgjEks3zoW7m6FMioN_ToNgYcU5kKM
// 12.977822, 77.592624

// link to google
//https://developers.google.com/maps/documentation/static-maps/intro


// json parser


// Getting the key for google

// take care of the dex error - This may crop up WHENyour app consumes excess memory!!!

// Manifest

// Make sure to add play services lib in gradel - emulator and device

// cahnge activity_maps.xml( copy and paste )

// GooglePlayServicesNotAvailable()
// convert lat lng to address

// create a menu to add options  512

// goto location to reach 405

// Marker 447

// Geo fencing adding a circle click on circle

// move marker

// Navigation Runtime permision 621 results 639 Enable_Navagiation 658

// stop Navigation

// Directions 748


// list near by places 886

//AIzaSyDNHQgjEks3zoW7m6FMioN_ToNgYcU5kKM

// https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-12.977822,77.592624&radius=500&type=restaurant&keyword=cruise&key=AIzaSyDNHQgjEks3zoW7m6FMioN_ToNgYcU5kKM

/*

Supported list of places while searching

    https://developers.google.com/places/supported_types

Supported url


    // Google Places serach url's
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
 
   display map and pic on web page

   http://maps.google.com/maps?f=q&source=s_q&hl=en&geocode=
&q=230+East+51st+St.,New+York,NY&sll=37.0625,-95.677068&sspn=
33.29802,56.601563&ie=UTF8&ll=40.755937,-73.969139&spn=
0.000937,0.001365&z=19

 */


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    Handler mUiHandler;
    public static int MY_PERMISSIONS_REQUEST_SEND_SMS = 101;

    int PLACE_PICKER_REQUEST = 1;
    public static int mCount = 0;

    private GoogleApiClient googleApiClient; //mMap;
    private GoogleMap mMap;
    Geocoder geoCoder;

    private LocationListener locationListener;
    private LocationManager locationManager;

    LocationRequest mLocationRequest;

    double travelSpeed = 0 ;

    String message;

    GoogleApiClient mGoogleApiClient;


    // LatLng lat1;
    Marker marker;

    //   ArrayList<Latlng> latlngArrayLiist = new ArrayList<Latlng>();  // This can record all the x,y from src to dest
    //   So that you can map it back..

    EditText addressText, finalAddressText, editText;

    //  Used in static Directions
    LatLng finalAddressPos, addressPos;

    boolean setDestination = false;

    // Used to get the default address
    // remember most of the fields in the address obj is Empty!!!

    List<android.location.Address> addresses1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        // if play service dose not exists then finish() the activity..
        // AAA
        if ( GooglePlayServicesAvailable() ) {  // 231
            Toast.makeText(this, "Connected to play services..", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_maps);


        } else {
            Toast.makeText(this, "Please Install play services..", Toast.LENGTH_SHORT).show();
            finish();
        }


        addressText      = (EditText) findViewById(R.id.addressEditText);
        finalAddressText = (EditText) findViewById(R.id.finalAddressEditText);

        editText = (EditText) findViewById(R.id.locationToReach);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //BBB
        // Lets do the reverse geo coding..
        Toast.makeText(this, GetTheAddressOfLatLng(12.977822, 77.592624), Toast.LENGTH_SHORT).show();
    }

    private String GetTheAddressOfLatLng(double LATITUDE, double LONGIITUDE) {

        String strAdr = "";
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<android.location.Address> address = geoCoder.getFromLocation(LATITUDE, LONGIITUDE, 1);

            if (address != null) {

                android.location.Address returnAddress = address.get(0);

                StringBuilder stringBuilder = new StringBuilder("");

                for (int i = 0; i < returnAddress.getMaxAddressLineIndex(); i++) {
                    stringBuilder.append(returnAddress.getAddressLine(i)).append("\n");

                }
                strAdr = stringBuilder.toString();
                Log.d("tag", " Address ; " + stringBuilder.toString());


            } else {
                Log.d("tag", " Address  not found ; ");


            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return strAdr;
    }

    public boolean GooglePlayServicesAvailable() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(isAvailable)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Oops!! cant load the play services...", Toast.LENGTH_SHORT).show();
        }

        return false;

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /////////////////////////// phase 1 and start the location tracker...
        // add this in enu too...
        // This will be used in location tracker...
        runTime_Permissions();        // required to Eable_locationManaer
        //  Enable_LocationManager();   // this will shift for the current location..






        /////////////////////
        if (mMap != null) {
            ////// phase 5 to drag icon on map - goto add .dragable(true) show it then come here..

            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                    Geocoder geoCoder = new Geocoder(MapsActivity.this);
                    //  geoCoder = new Geocoder(MapsActivity.this);
                    LatLng latlng = marker.getPosition();
                    double lat = latlng.latitude;
                    double lng = latlng.longitude;

                    List<android.location.Address> list = null;

                    try {
                        list = geoCoder.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    android.location.Address address = list.get(0);

                    marker.setTitle(address.getLocality());
                    marker.showInfoWindow();
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    // Refractor this function to MoveMarkerInfo add this function in onMarkerDragStart and try it...
                    Geocoder geoCoder = new Geocoder(MapsActivity.this);
                    //  geoCoder = new Geocoder(MapsActivity.this);
                    LatLng latlng = marker.getPosition();
                    double lat = latlng.latitude;
                    double lng = latlng.longitude;

                    List<android.location.Address> list = null;

                    try {
                        list = geoCoder.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    android.location.Address address = list.get(0);

                    marker.setTitle(address.getLocality());
                    marker.showInfoWindow();

                }
            });

            /////////////////////////////


            // impliment methods
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View view = getLayoutInflater().inflate(R.layout.marker_info, null);

                    TextView viewLocality  = (TextView) view.findViewById(R.id.textViewLocality);
                    TextView viewLat       = (TextView) view.findViewById(R.id.textViewLat);
                    TextView viewLng       = (TextView) view.findViewById(R.id.textViewLng);
                    TextView viewSnippet   = (TextView) view.findViewById(R.id.textViewSnippet);

                    LatLng latLng = marker.getPosition();

                    viewLocality.setText(marker.getTitle());
                    viewLat.setText("Latitude : " + latLng.latitude);
                    viewLng.setText("Longitude : " + latLng.longitude);
                    viewSnippet.setText(marker.getSnippet());

                    return view;
                }
            });
        }

///////////////////////  mMap != null if condition ends here...


        // Add a marker in Sydney and move the camera
        LatLng Bangalore = new LatLng(12.977822, 77.592624);
        mMap.addMarker(new MarkerOptions().position(Bangalore).title("Marker in Bangalore"));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Bangalore, (float) 17.0));


        mMap.addCircle(new CircleOptions()
                .center(new LatLng(12.977822, 77.592624))
                .radius(1500)
                .strokeWidth(7)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(25, 255, 255, 255))
                .clickable(true)
        );

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {


            @Override
            public void onCircleClick(Circle circle) {

                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });


        //  mMap.getUiSettings().setMyLocationButtonEnabled(true);
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }

        // CCC
        mMap.setMyLocationEnabled(true); // adds a compass on to p of the map by clicking on it sets the current location
        // needs uses permission Coarse & FINE_location
//
//        // Lets bring in Menu to change the appr. of th emap..
//


        // mMap.setMyLocationEnabled(true);
        // mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }


    public void LocationToReach(View view) {


        // EditText
        //editText = (EditText) findViewById(R.id.locationToReach);
        String location = editText.getText().toString();


        Geocoder geocoder = new Geocoder(this);

        List<android.location.Address> list = null;

        try {
            list = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        android.location.Address address = list.get(0);

        String locality = address.getLocality();
        String adressLine1 = address.getAddressLine(0);
        String url = address.getUrl();
        Log.d("tag", " : url " + url);

        Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();
        editText.setText(" Phone : " + address.getPhone() + " Premi : " + address.getPremises());  // prints NULL.. Guys I have warned you at the begining

        goToLocationZoom(lat, lng, 16.0);

      //  setmarker(locality, lat, lng); //Have all the code here then use refactor extrate method to put this into a function


    }

    Circle circle;

    private void setmarker(String locality, double lat, double lng) {

        if (marker != null) {
            //marker.remove();
            RemoveMarkerAndCircle();
        }

        MarkerOptions markerOptions = new MarkerOptions()
                .title(locality)
                .position(new LatLng(lat, lng))
                .snippet("My Favourite place")
                .draggable(true)     // lets add drag listener....

                // .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));


        marker = mMap.addMarker(markerOptions);

        circle = DrawCircle(new LatLng(lat, lng));

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {


            @Override
            public void onCircleClick(Circle circle) {

                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });


    }

    private Circle DrawCircle(LatLng latLng) {

        // mMap.addCircle(new CircleOptions()
        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(1000)
                .strokeWidth(7)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(25, 255, 255, 255))
                .clickable(true);

        return mMap.addCircle(circleOptions);


    }

    private void RemoveMarkerAndCircle() {
        marker.remove();
        ;
        marker = null;
        circle.remove();
        circle = null;
    }


    private void goToLocationZoom(double lat, double lng, double zoom) {

        LatLng latlng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, (float) zoom);
        mMap.moveCamera(cameraUpdate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Phase 111111 add to menu xml file last two lines

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapTypeNone:

                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);

                break;


            case R.id.mapTypeNormal:

                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:

                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeybrid:

                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.mapTypeTerrain:

                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;

            case R.id.locationManagerStart:

                Enable_LocationManager();

                break;

            case R.id.locationManagerStop:

                DestroyLocationManager();

                break;

            case R.id.listPlace:

                try {
                    ListNearByPlaces();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }

                break;


        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        DestroyLocationManager();


    }

    public void DestroyLocationManager() {

        if (locationManager != null) {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(locationListener);

            //This will stop the app
        }

    }


    /////////phase 22222


    /////////////   permissions

    // ArrayList<Location> storeLocation  = new ArrayList<Location>();

    // Put this part of the code into service...
    // Use Job Schedulears...

    private void Enable_LocationManager() {


        // geoCoder = new Geocoder(this, Locale.getDefault());
        // This happens to a long running task So this has to be in a thread ( Child )
        //  best opt is to have this chunk of code in services...




        //////
        locationListener = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {

                //calculating the speed with getSpeed method it returns speed in m/s so we are converting it into kmph
                /*
                The speed (Sp) conversion formulas
                 How to convert meter per second to kilometer per hour [m/sec to km/h]:  3.6 × Spm/sec = Spkm/h

                 How many (kilometers per hour) in a meter per second:  If m/sec = 1 then km/h = 3.6 × 1 = 3.6 km/h
                  Example:  If m/sec = 55 then km/h = 3.6 × 55 = 198 km/h

                   How to convert kilometer per hour to meter per second [km/h to m/sec]:  0.27777777777778 × Spkm/h = Spm/sec
                   v = d/t : 1000 mtr = 1km 3600 sec = 1 hr
                   3600/1000 = 3.6

                 or

                    18/5 = 3.6
                 */
                travelSpeed = location.getSpeed() * 3.6;  //  or // 18 / 5;
                editText.setText(" Travel Speed : "+new DecimalFormat("#.##").format(travelSpeed) + " km/hr");


                // storeLocation.add(location);  // retrive the data on push of a button.
                //storeLocation.size();


                // 2nd

//                try {
//                    addresses1 = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                String locality = addresses1.get(0).getLocality();
//                editText.setText(locality);

                // move the markers in sync with lat & lng
//                if (marker != null) {
//                    mCount++;
//                    if( mCount > 1) {
//                        marker.remove();
//                    }
//                }
///             get locality


                ////////
                // Add a marker in Sydney and move the camera
                LatLng CurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                //   latlngArrayLiist.add(CurrentLocation);

                //  sendLocationSMS("7795862629", CurrentLocation);
                //  marker =  mMap.addMarker(new MarkerOptions().position(Bangalore).title("Marker in Bangalore"));
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Bangalore, (float) 16.0));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentLocation));


                ///// custom marker...
                // z
               // Toast.makeText(this, GetTheAddressOfLatLng(location.getLatitude(),location.getLongitude()), Toast.LENGTH_SHORT).show();

                // Update the server about the current location and u r ID - Cell number..

                // Plotting the map all over again...
//                for(i = 0 ; i<= latlngArrayLiist.size();i++) {
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngArrayLiist.get(i)));
//                }


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            //// 1st
            @Override
            public void onProviderDisabled(String s) {



                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        };

        // 3rd..
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


//        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);// replace 0 by Time freq 1000*3
        //LocationManager.NETWORK_PROVIDER






    }


    //////////////////   Get Directions


    public void getDirections(View view) {

        String startingAddress = addressText.getText().toString();
        String finalAddress = finalAddressText.getText().toString();


        if (startingAddress.equals("") || finalAddress.equals("")) {


            Toast.makeText(this, "Please fill in the address...", Toast.LENGTH_SHORT).show();
        }

        // Lets call ASYNC thread now

        new GetDirections().execute(startingAddress, finalAddress);
    }


    // The AsyncTask that gets directions
    class GetDirections extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            // Get the starting address
            String startAddress = params[0];

            // Replace the spaces with %20
            startAddress = startAddress.replaceAll(" ", "%20");

            // Get the lat and long for our address
            getLatLong(startAddress, false);

            // Get the destination address
            String endingAddress = params[1];

            // Replace the spaces with %20
            endingAddress = endingAddress.replaceAll(" ", "%20");

            // Get lat and long for the destination address and pass true
            getLatLong(endingAddress, true);

            return null;

        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // Create the URL for Google Maps to get the directions
            String geoUriString = "http://maps.google.com/maps?saddr=" +
                    addressPos.latitude + "," +
                    addressPos.longitude + "&daddr=" +
                    finalAddressPos.latitude + "," +
                    finalAddressPos.longitude;

            //////////

//            http://maps.google.com/maps?f=q&source=s_q&hl=en&geocode=
//&q=230+East+51st+St.,New+York,NY&sll=37.0625,-95.677068&sspn=
//                    33.29802,56.601563&ie=UTF8&ll=40.755937,-73.969139&spn=
//                    0.000937,0.001365&z=19


            ///////////
            // Call for Google Maps to open
            Intent mapCall = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUriString));

            startActivity(mapCall);

        }

    }
/*


{
   "results" : [
      {
         "address_components" : [
            {
               "long_name" : "Bengaluru",
               "short_name" : "Bengaluru",
               "types" : [ "locality", "political" ]
            },
            {
               "long_name" : "Karnataka",
               "short_name" : "KA",
               "types" : [ "administrative_area_level_1", "political" ]
            },
            {
               "long_name" : "India",
               "short_name" : "IN",
               "types" : [ "country", "political" ]
            },
            {
               "long_name" : "560043",
               "short_name" : "560043",
               "types" : [ "postal_code" ]
            }
         ],
         "formatted_address" : "No.111/B, Ground Floor, Tuscun Signature, 80 Feet Main Rd, Opp Rama Temple Complex, 1st Block, HBR Layout, Kalyan Nagar, Bengaluru, Karnataka 560043, India",
         "geometry" : {
            "location" : {
               "lat" : 13.0207543,
               "lng" : 77.6288084
            },
            "location_type" : "GEOMETRIC_CENTER",
            "viewport" : {
               "northeast" : {
                  "lat" : 13.0221032802915,
                  "lng" : 77.63015738029151
               },
               "southwest" : {
                  "lat" : 13.0194053197085,
                  "lng" : 77.6274594197085
               }
            }
         },
         "place_id" : "ChIJk6F1wxgXrjsR6jJJ0eqH8ec",
         "plus_code" : {
            "compound_code" : "2JCH+8G Bengaluru, Karnataka, India",
            "global_code" : "7J5V2JCH+8G"
         },
         "types" : [ "bakery", "establishment", "food", "point_of_interest", "store" ]
      }
   ],
   "status" : "OK"
}
 */
    protected void getLatLong(String address, boolean setDestination) {

        // Define the uri that is used to get lat and long for our address
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                address + "&sensor=false";

        //   http://maps.google.com/maps/api/geocode/json?Aaddress=393,2nd block, 2nd  main, 1st stage hbr layout Bangalore&sensor=false

        // Use the get method to retrieve our data
        HttpGet httpGet = new HttpGet(uri);

        // Acts as the client which executes HTTP requests
        HttpClient client = new DefaultHttpClient();

        // Receives the response from our HTTP request
        HttpResponse response;

        // Will hold the data received
        StringBuilder stringBuilder = new StringBuilder();

        try {

            // Get the response of our query
            response = client.execute(httpGet);

            // Receive the entity information sent with the HTTP message
            HttpEntity entity = response.getEntity();

            // Holds the sent bytes of data
            InputStream stream = entity.getContent();
            int byteData;

            // Continue reading data while available
            while ((byteData = stream.read()) != -1) {
                stringBuilder.append((char) byteData);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double lat = 0.0, lng = 0.0;

        // Holds key value mappings
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            // Get the returned latitude and longitude
            lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            // Change the lat and long depending on if we want to set the
            // starting or ending destination
            if (setDestination) {

                finalAddressPos = new LatLng(lat, lng);

            } else {
                addressPos = new LatLng(lat, lng);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////////----------------------//////////////////////



    private void ListNearByPlaces() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        // It will show the UI below to pick a place for you, and you can get the place info by using below:
        // API .. Display Tourist attractions with in 50 km or 100 or 200 km radious


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                String toastMsg = String.format("Place: %s", place.getName());


                Toast.makeText(this, toastMsg + " Phone : " + place.getPhoneNumber() + "\n WEB URI " + place.getWebsiteUri(), Toast.LENGTH_LONG).show();

                ShowAleartDialogBox(place.getName().toString(), place.getWebsiteUri() + "\n" + place.getPlaceTypes() + "\n" + place.getAddress() +
                        "\n" + place.getPhoneNumber().toString());


                /////// pass this on to a dialog!!

//                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                                //Uncomment the below code to Set the message and title from the strings.xml file
//                                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
//
//                                //Setting message manually and performing action on button click
//                                builder.setMessage((CharSequence) place.getWebsiteUri())
//                                        .setCancelable(false)
//                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                finish();
//                                            }
//                                        })
//                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialog, int id) {
//                                                        //  Action for 'NO' Button
//                                                        dialog.cancel();
//                                                    }
//                                                });
//                                //Creating dialog box
//                                AlertDialog alert = builder.create();
//                                //Setting the title manually
//                                alert.setTitle("AlertDialogExample");
//                                alert.show();
                //  setContentView(R.layout.activity_main);

                /////////////////////

//                View view = getLayoutInflater().inflate(R.layout.marker_info, null);
//
//                TextView viewLocality = (TextView) view.findViewById(R.id.textViewLocality);
//                TextView viewLat = (TextView) view.findViewById(R.id.textViewLat);
//                TextView viewLng = (TextView) view.findViewById(R.id.textViewLng);
//                TextView viewSnippet = (TextView) view.findViewById(R.id.textViewSnippet);
//
//               // LatLng latLng = marker.getPosition();
//
//                viewLocality.setText(place.getName());
//                viewLat.setText("Phone Number : " + place.getPhoneNumber());
//                viewLng.setText("Longitude : " + place.getWebsiteUri());
//                viewSnippet.setText(place.getAddress());
                //////////
            }
        }

    }

    public void ShowAleartDialogBox(String title, String msg) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        // builder.setCancelable(true);

        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MapsActivity.this, "Clicked on YES...", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MapsActivity.this, "Clicked on No.....", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();

    }


    // send an sms
    PendingIntent sentPI;
    String phoneNumber ="+917795862629";
    public void sendLocationSMS(String phoneNumber, LatLng currentLocation) {
        SmsManager smsManager = SmsManager.getDefault();
        StringBuffer smsBody = new StringBuffer();
        smsBody.append("http://maps.google.com?q=");
        smsBody.append(currentLocation.latitude);
        smsBody.append(",");
        smsBody.append(currentLocation.longitude);
        message = smsBody.toString();
        sendSMSMessage();

//        sentPI = PendingIntent.getBroadcast(this, 0,new Intent("SENTSMS"), 0);
//        smsManager.sendTextMessage(phoneNumber, null, smsBody.toString(), sentPI, null);


        // works but we have to enter cell number and push..
//        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//        sendIntent.putExtra("sms_body",smsBody.toString());// "default content");
//        sendIntent.setType("vnd.android-dir/mms-sms");
//        startActivity(sendIntent);



    }
    //////////////////////////////////////

    protected void sendSMSMessage() {


//        phoneNo = txtphoneNo.getText().toString();
//        message = txtMessage.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }
    private boolean runTime_Permissions() {

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

            return true;

        }
        return false;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Enable_LocationManager();
            } else {
                runTime_Permissions();

            }
            if( requestCode ==  MY_PERMISSIONS_REQUEST_SEND_SMS) {

                Log.d("tag"," MY_PERMISSIONS_REQUEST_SEND_SMS");
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }


        }


    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//                    Toast.makeText(getApplicationContext(), "SMS sent.",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }

  //  }

    ///////////////////////////////////////



}









