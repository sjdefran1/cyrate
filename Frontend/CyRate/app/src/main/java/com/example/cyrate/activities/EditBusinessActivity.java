package com.example.cyrate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cyrate.Logic.BusinessServiceLogic;
import com.example.cyrate.Logic.BusinessInterfaces.businessStringResponse;
import com.example.cyrate.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

public class EditBusinessActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navView;
    ImageView open_menu;

    EditText busName;
    EditText busType;
    EditText busHours;
    EditText busLocation;
    EditText priceGauge;
    EditText photoUrl;
    Button updateBtn;

    int busId;

    BusinessServiceLogic businessServiceLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business);
        Bundle extras = getIntent().getExtras();

        // Inputs
        busName = (EditText) findViewById(R.id.et_busName);
        busType = (EditText) findViewById(R.id.et_busType);
        busHours = (EditText) findViewById(R.id.et_busHours);
        busLocation = (EditText) findViewById(R.id.et_busLocation);
        priceGauge = (EditText) findViewById(R.id.et_priceGauge);
        photoUrl = (EditText) findViewById(R.id.et_photoUrl);
        updateBtn = (Button) findViewById(R.id.btn_submit);

        busName.setText(extras.getString("NAME"));
        busType.setText(extras.getString("CATEGORY"));
        busHours.setText(extras.getString("HOURS"));
        busLocation.setText(extras.getString("ADDRESS"));
        priceGauge.setText(extras.getString("PRICE_GAUGE"));
        photoUrl.setText(extras.getString("IMAGE"));
        busId = extras.getInt("ID");

        // Navigation menu stuff
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        open_menu = (ImageView) findViewById(R.id.open_menu_icon);

        navigationDrawer();
        drawerLayout.setScrimColor(getResources().getColor(R.color.red));


        // Submit Logic
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if any inputs are not field
                if (busName.getText().toString().isEmpty() || busType.getText().toString().isEmpty() || busHours.getText().toString().isEmpty() ||
                        busLocation.getText().toString().isEmpty() || priceGauge.getText().toString().isEmpty() || photoUrl.getText().toString().isEmpty()
                ) {
                    Toast.makeText(EditBusinessActivity.this, "Complete All Inputs", Toast.LENGTH_LONG).show();
                } else {
                    businessServiceLogic = new BusinessServiceLogic();

                    String name = busName.getText().toString();
                    String type = busType.getText().toString();
                    String hours = busHours.getText().toString();
                    String location = busLocation.getText().toString();
                    String price = priceGauge.getText().toString();
                    String photo = photoUrl.getText().toString();

                    busName.setText("");
                    busType.setText("");
                    busHours.setText("");
                    busLocation.setText("");
                    priceGauge.setText("");
                    photoUrl.setText("");


                    try {
                        businessServiceLogic.editBusiness(busId, name, type, hours, location, price, photo,
                                new businessStringResponse() {
                                    @Override
                                    public void onSuccess(String s) {
                                        Toast.makeText(EditBusinessActivity.this, s, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onError(String s) {
                                        Toast.makeText(EditBusinessActivity.this, s, Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // When menu is open and back button is pressed, we just close the menu instead of going
    // back a page
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void navigationDrawer() {
        // Navigation Drawer
        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);

        open_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_allBusinesses:
                intent = new Intent(EditBusinessActivity.this, BusinessListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_addBusiness:
                intent = new Intent(EditBusinessActivity.this, AddBusinessActivity.class);
                startActivity(intent);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}