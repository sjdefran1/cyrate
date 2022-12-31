package com.example.cyrate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.cyrate.Logic.ReviewInterfaces.reviewStringResponse;
import com.example.cyrate.Logic.ReviewServiceLogic;
import com.example.cyrate.activities.AddReviewActivity;
import com.example.cyrate.activities.IndividualReviewActivity;
import com.example.cyrate.activities.MainActivity;
import com.example.cyrate.activities.ReviewListActivity;

import org.json.JSONException;

public class EditReviewActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText headingTxt, bodyTxt;
    ImageView submitBtn, backBtn;

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);

        ratingBar = findViewById(R.id.ratingBar_editReview);
        headingTxt = findViewById(R.id.reviewHeading_editReview);
        bodyTxt = findViewById(R.id.reviewBody_editReview);
        backBtn = findViewById(R.id.back_btn_editReview);
        submitBtn = findViewById(R.id.submitReview_editReview);

        extras = getIntent().getExtras();

        ratingBar.setRating(extras.getInt("RATING_VAL"));
        headingTxt.setText(extras.getString("REVIEW_HEADING"));
        bodyTxt.setText(extras.getString("REVIEW_BODY"));


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditReviewActivity.this, IndividualReviewActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bodyTxt.getText().toString().isEmpty() || headingTxt.toString().isEmpty()){
                    Toast.makeText(EditReviewActivity.this, "Fill out the Review Body or Heading", Toast.LENGTH_LONG).show();
                }
                else{
                    String reviewTextBody = bodyTxt.getText().toString();
                    String reviewHeadingTxt = headingTxt.getText().toString();
                    int ratingVal = (int) ratingBar.getRating();
                    int busId = extras.getInt("ID");

                    ReviewServiceLogic reviewServiceLogic = new ReviewServiceLogic();

                    try {
                        int reviewId = extras.getInt("REVIEW_ID");
                        reviewServiceLogic.editReview(reviewId, reviewTextBody, reviewHeadingTxt, ratingVal,
                                new reviewStringResponse() {
                                    @Override
                                    public void onSuccess(String s) {
                                        Log.d("ADD REVIEW ON SUCCESS", s);
                                        Toast.makeText(EditReviewActivity.this, "Review Updated!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(EditReviewActivity.this, ReviewListActivity.class);
                                        intent.putExtras(extras);

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(intent);
                                            }
                                        }, 1000);
                                    }

                                    @Override
                                    public void onError(String s) {
                                        Toast.makeText(EditReviewActivity.this, "ERROR IN EDIT REVIEW", Toast.LENGTH_LONG).show();
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
}