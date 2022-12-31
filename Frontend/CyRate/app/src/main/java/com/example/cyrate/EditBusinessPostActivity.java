package com.example.cyrate;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyrate.Logic.BusinessInterfaces.businessStringResponse;
import com.example.cyrate.Logic.BusinessServiceLogic;
import com.example.cyrate.activities.AddBusinessPostActivity;
import com.example.cyrate.activities.BusinessPostFeed;
import com.example.cyrate.net_utils.Const;
import com.example.cyrate.net_utils.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.Base64;

public class EditBusinessPostActivity extends AppCompatActivity {

    Bundle extras;

    TextView busName;
    ImageView busImage, backBtn, photoVerify;
    Button submitBtn, addPhoto;
    EditText postTxt_et;

    Bitmap bitmap;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business_post);

        extras = getIntent().getExtras();

        busName = findViewById(R.id.editPost_busName);
        busImage = findViewById(R.id.editPost_busPhoto);
        backBtn = findViewById(R.id.back_btn_editPost);
        submitBtn = findViewById(R.id.editPost_submit);
        postTxt_et = findViewById(R.id.editPost_postTxt);
        addPhoto = findViewById(R.id.editPost_photoUrl);
        photoVerify = findViewById(R.id.editPost_photoCheck);

        new ImageLoaderTask(extras.getString("IMAGE"), busImage).execute();
        busName.setText(extras.getString("NAME"));
        postTxt_et.setText(extras.getString("POST_TEXT"));

        // Get our byte array from extras, and convert it into a bitmap
        byte[] byteArray = extras.getByteArray(Const.BUS_POST_BITMAP);
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditBusinessPostActivity.this, BusinessPostFeed.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postTxt_et.getText().toString().isEmpty()){
                    Toast.makeText(EditBusinessPostActivity.this, "Fill out your message!", Toast.LENGTH_LONG).show();
                }
                // Make sure a photo is added
                if(bitmap == null){
                    Toast.makeText(EditBusinessPostActivity.this, "Add a photo!", Toast.LENGTH_LONG).show();
                }
                else{
                    String postTxt = postTxt_et.getText().toString();
                    String blobPhoto = Utils.imageToString(bitmap);

                    BusinessServiceLogic businessServiceLogic = new BusinessServiceLogic();

                    int postId = extras.getInt("POST_ID");
                    try {
                        businessServiceLogic.editPost(postId, postTxt, blobPhoto, new businessStringResponse() {
                            @Override
                            public void onSuccess(String s) {
                                Log.d("EDIT POST ON SUCCESS", s);
                                Toast.makeText(EditBusinessPostActivity.this, "Post Updated!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(EditBusinessPostActivity.this, BusinessPostFeed.class);
                                intent.putExtras(extras);

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(intent);
                                    }
                                }, 2000);
                            }

                            @Override
                            public void onError(String s) {
                                Toast.makeText(EditBusinessPostActivity.this, "ERROR IN EDIT POST", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Utils.IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Utils.IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                if(bitmap != null){
                    photoVerify.setVisibility(View.VISIBLE);
                    Toast.makeText(EditBusinessPostActivity.this, "Photo Updated!", Toast.LENGTH_SHORT).show();

                }
                else{
                    photoVerify.setVisibility(View.INVISIBLE);
                }
                Log.d("Bitmap", bitmap.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}