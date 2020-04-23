package com.example.hack1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GoogleMapDemo extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{
    GoogleMap map;
    Location currentLocation;
    private GoogleApiClient client;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    Button b1,b2,b3;
    private Marker currentLocationMarker;
    int PROXIMITY_RADIUS=10000;
    double latitude,longitude;
    private static final int REQUEST_LOCATION_CODE=99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_demo);
        b1=(Button)findViewById(R.id.Bhospital);
        b2=(Button)findViewById(R.id.Bsearch);
        b3=(Button)findViewById(R.id.btnGetDirection);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        map.clear();
                        String hospital="hospital";
                        String url=getUrl(latitude,longitude,hospital);
                        Object dataTransfer[]=new Object[2];
                        dataTransfer[0]=map;
                        dataTransfer[1]=url;
                        GetNearByPlacesData getNearByPlacesData=new GetNearByPlacesData();
                        getNearByPlacesData.execute(dataTransfer);
                        Toast.makeText(GoogleMapDemo.this,"Showing Nearby Hospitals",Toast.LENGTH_LONG).show();


                }


        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        EditText tf_location = (EditText) findViewById(R.id.TFlocation);
                        String location = tf_location.getText().toString();
                        List<Address> addressList = null;
                        MarkerOptions mo = new MarkerOptions();
                        if (!location.equals("")) {
                            Geocoder geocoder = new Geocoder(GoogleMapDemo.this);
                            try {
                                addressList = geocoder.getFromLocationName(location, 5);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < addressList.size(); i++) {
                                Address myAddress = addressList.get(i);
                                LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                                mo.position(latLng);
                                mo.title("Your Search result");
                                map.addMarker(mo);
                                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            }

                        }
                    }




        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Toast.makeText(GoogleMapDemo.this, "Tap on your Search Location to redirect on Google Map", Toast.LENGTH_LONG).show();
                    }



        });
    }

    private String getUrl(double latitude,double longitude,String nearbyPlace)
    {
        StringBuilder googlePlaceUrl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyCcsluWHXyTvxg4aK03QKy02O4EiR1Zlxg");
        return googlePlaceUrl.toString();

    }
    protected synchronized void buildGoogleApiClient()
    {
        client=new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }
    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                    {
                        if(client==null)
                        {
                            buildGoogleApiClient();
                        }
                        map.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied!",Toast.LENGTH_LONG).show();
                }
                return;
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation=location;
        if(currentLocationMarker!=null)
        {
            currentLocationMarker.remove();
        }
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationMarker=map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomBy(10));
        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        LatLng Uttarakhand=new LatLng(29.942982, 79.519698);
        map.addMarker(new MarkerOptions().position(Uttarakhand).title("Uttarakhand"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Uttarakhand));
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
        }
    }
}
