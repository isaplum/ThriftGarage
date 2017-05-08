package com.example.krixniemann.thriftgarage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
//import com.google.android.gms.location.places.Places;
//import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by krixniemann on 5/6/2017.
 */

public class CreateActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    TextView mTextView;
    EditText etAddressy;
    private Marker entry;
    private GoogleMap mMap;
    Button submit;
    private String addressy = "street";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mTextView = (TextView) findViewById(R.id.tvDisplay);
        etAddressy = (EditText) findViewById(R.id.etAddress);
        // PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
       MapFragment mapFragment =
                (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)

                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .build();



        submit = (Button) findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                if (mMap !=null)
                {
                    mMap.clear();
                }

        addressy = etAddressy.getText().toString();
                LatLng addresso = getLocationFromAddress(addressy);
               mMap.addMarker(new MarkerOptions().position(addresso).title("Ugh"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(addresso, 12.0f));
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                String key = databaseReference.push().getKey();
                databaseReference.child(key).child("place").push().setValue(addresso);
            }

    });
    }
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;






    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public LatLng getLocationFromAddress(String strAddress)
    {
        Geocoder coder = new Geocoder(this);
        List<android.location.Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            android.location.Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return p1;
    }
}