package com.example.cyrate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyrate.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class FindUsActivity extends FragmentActivity implements OnMapReadyCallback {
    TextView address, phoneNum, hours;
    ImageView back_btn;
    GoogleMap map;

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_us);

        back_btn = (ImageView) findViewById(R.id.back_button_image);
        address = (TextView) findViewById(R.id.address_txt);
        phoneNum = (TextView) findViewById(R.id.phoneNumber_txt);
        hours = (TextView) findViewById(R.id.hours_txt);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        extras = getIntent().getExtras();

        String hoursString = extras.getString("HOURS").split("\\|")[0];


        address.setText(extras.get("ADDRESS").toString());
        phoneNum.setText("(515) 293-0180");
        hours.setText(hoursString);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindUsActivity.this, IndividualBusinessActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng address = getLocationFromAddress(this, extras.get("ADDRESS").toString());
        map.addMarker(new MarkerOptions().position(address).title("We're Here!"));
        float zoomLevel = 16.0f; //This goes up to 21
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(address, zoomLevel));
    }

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }
}