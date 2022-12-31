package com.example.cyrate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyrate.Logic.ReviewInterfaces.getReviewsResponse;
import com.example.cyrate.Logic.ReviewServiceLogic;
import com.example.cyrate.NavMenuUtils;
import com.example.cyrate.R;
import com.example.cyrate.adapters.ReviewListAdapter;
import com.example.cyrate.models.ReviewRecyclerViewInterface;
import com.example.cyrate.models.ReviewListCardModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PersonalReviewListActivity extends AppCompatActivity implements ReviewRecyclerViewInterface, NavigationView.OnNavigationItemSelectedListener {

    ReviewServiceLogic reviewServiceLogic;
    ReviewListAdapter reviewListAdapter;
    Bundle extras;
    ArrayList<ReviewListCardModel> reviewListCardModels = new ArrayList<>();

    DrawerLayout drawerLayout;
    NavigationView navView;
    ImageView navMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_review_list);

        extras = getIntent().getExtras();

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        navMenu = (ImageView) findViewById(R.id.open_menu_icon);

        NavMenuUtils.hideMenuItems(navView.getMenu());

        navigationDrawer();

        drawerLayout.setScrimColor(getResources().getColor(R.color.red));


        RecyclerView recyclerView = findViewById(R.id.reviewList_recyclerView);
        TextView emptyView = findViewById(R.id.empty_view);


        // Set this to non-visible initially
        emptyView.setVisibility(View.GONE);

        reviewServiceLogic = new ReviewServiceLogic();
        reviewListAdapter = new ReviewListAdapter(
                this, reviewListCardModels, this

        );
        recyclerView.setAdapter(reviewListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            Log.d("my reviews:", "trying to set up review models");

            setUpReviewModels(recyclerView, emptyView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // When menu is open and back button is pressed, we just close the menu instead of going
    // back a page
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    private void navigationDrawer() {
        // Navigation Drawer
        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);
        navView.setCheckedItem(R.id.nav_my_reviews);

        navMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.nav_my_reviews){
            NavMenuUtils.onNavItemSelected(menuItem, PersonalReviewListActivity.this);
        }
        else{
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUpReviewModels(RecyclerView recyclerView, TextView emptyView) throws JSONException {
//        int busId = extras.getInt("ID");
        reviewServiceLogic.getReviewsByUser(MainActivity.globalUser.getUserId(), new getReviewsResponse() {
            @Override
            public void onSuccess(List<ReviewListCardModel> list) {

                for (int i = 0; i < list.size(); i++) {
                    reviewListCardModels.add(list.get(i));
                }
                Log.d("TEST 2", "IN HERE");
                reviewListAdapter.notifyDataSetChanged();

                if(list.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else{
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
                TextView reviewCount = findViewById(R.id.reviewsCount);
                reviewCount.setText(String.valueOf(list.size()));
            }

            @Override
            public void onError(String s) {
                Log.d("TEST 2", s);
                Toast.makeText(PersonalReviewListActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    // onClick for each card in the list
    public void onReviewClick(int position) {
        Intent intent = new Intent(PersonalReviewListActivity.this, IndividualReviewActivity.class);


        intent.putExtra("PREVIOUS_ACTIVITY", "PersonalReviewListActivity");
        intent.putExtra("REVIEWER_NAME", reviewListCardModels.get(position).getReviewUser().getFullName());
        intent.putExtra("RATING_VAL", reviewListCardModels.get(position).getRateVal());
        intent.putExtra("REVIEW_BODY", reviewListCardModels.get(position).getReviewText());
        intent.putExtra("REVIEWER_PROFILE_PIC", reviewListCardModels.get(position).getReviewUser().getPhotoUrl());
        intent.putExtra("REVIEWER_USERNAME", reviewListCardModels.get(position).getReviewUser().getUsername());

        startActivity(intent);
    }

    public void onBusinessClick(int position){
        return;
    }
}