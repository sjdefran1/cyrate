package com.example.cyrate.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyrate.EditBusinessPostActivity;
import com.example.cyrate.ImageLoaderTask;
import com.example.cyrate.Logic.BusinessInterfaces.businessStringResponse;
import com.example.cyrate.Logic.BusinessServiceLogic;
import com.example.cyrate.Logic.ReviewInterfaces.reviewStringResponse;
import com.example.cyrate.R;
import com.example.cyrate.UserType;
import com.example.cyrate.activities.CommentThreadActivity;
import com.example.cyrate.activities.IndividualReviewActivity;
import com.example.cyrate.activities.MainActivity;
import com.example.cyrate.activities.ReviewListActivity;
import com.example.cyrate.models.BusinessPostCardModel;
//import com.example.cyrate.models.RecyclerViewInterface;
import com.example.cyrate.models.ReviewListCardModel;
import com.example.cyrate.net_utils.Const;
import com.example.cyrate.net_utils.Utils;

import org.json.JSONException;
import org.w3c.dom.Comment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used to populate the RecyclerView for the list of Posts by a
 * specific business
 */
public class BusinessFeedAdapter extends RecyclerView.Adapter<BusinessFeedAdapter.MyViewHolder> {

    Context ctx;
    ArrayList<BusinessPostCardModel> businessPostList;
    Bundle extras;

    /**
     *
     * @param ctx
     * @param businessPostList
     * @param extras
     */
    public BusinessFeedAdapter(
            Context ctx,
            ArrayList<BusinessPostCardModel> businessPostList,
            Bundle extras
    ){
        this.ctx = ctx;
        this.businessPostList = businessPostList;
        this.extras = extras;
    }

    @NonNull
    @Override
    public BusinessFeedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout. business_post_card, parent, false);
        return new BusinessFeedAdapter.MyViewHolder(view, ctx, extras, businessPostList);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessFeedAdapter.MyViewHolder holder, int position) {
        final byte[] imgBlob = businessPostList.get(position).getBlobPhoto();
        final Bitmap bitmap = BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length);
        holder.busPostPhoto.setImageBitmap(bitmap);

        new ImageLoaderTask(businessPostList.get(position).getBusiness().getPhotoUrl(), holder.busProfilePic).execute();

        holder.busName.setText(businessPostList.get(position).getBusiness().getBusName());
        holder.busPostDate.setText(businessPostList.get(position).getDate());
        holder.busPostText.setText(businessPostList.get(position).getPostTxt());
    }

    @Override
    public int getItemCount() {
        return businessPostList.size();
    }

    // Class necessary and is similar for having an onCreate method. Allows us to get all our views
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView busProfilePic, busPostPhoto, deleteIcon, editIcon, commentIcon;
        TextView busName, busPostDate, busPostText;

        public MyViewHolder(@NonNull View itemView, Context ctx, Bundle extras, ArrayList<BusinessPostCardModel> list) {
            super(itemView);

            busProfilePic = itemView.findViewById(R.id.busProfilePic);
            busPostPhoto = itemView.findViewById(R.id.busPost_photo);
            busName = itemView.findViewById(R.id.busPost_name);
            busPostDate = itemView.findViewById(R.id.busPost_date);
            busPostText = itemView.findViewById(R.id.busPost_bodyText);

            deleteIcon = itemView.findViewById(R.id.busPost_deleteIcon);
            editIcon = itemView.findViewById(R.id.busPost_editIcon);
            commentIcon = itemView.findViewById(R.id.busPost_comment);


            // Remove the delete icon if the current User is not the original reviewer or not an Admin
            deleteIcon.setVisibility(View.GONE);
            editIcon.setVisibility(View.GONE);


            // Update the thumbsUpIcon and CommentIcon position since we removed the deleteIcon
            ConstraintLayout cl = (ConstraintLayout) itemView.findViewById(R.id.busCard_constraintLayout);
            ConstraintSet cs = new ConstraintSet();
            cs.clone(cl);

            cs.setHorizontalBias(R.id.busPost_thumbsUp, (float) 0.4);
            cs.setHorizontalBias(R.id.busPost_comment, (float) 0.6);
            cs.applyTo(cl);

            if (MainActivity.globalUser.getUserType() == UserType.ADMIN) {

                cs.setHorizontalBias(R.id.busPost_thumbsUp, (float) 0.2);
                cs.setHorizontalBias(R.id.busPost_comment, (float) 0.4);
                cs.applyTo(cl);

                deleteIcon.setVisibility(View.VISIBLE);
                editIcon.setVisibility(View.VISIBLE);
            }

            commentIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ctx, CommentThreadActivity.class);
                    intent.putExtras(extras);
                    intent.putExtra(Const.ID_FOR_COMMENT, list.get(getAdapterPosition()).getPostId());
                    intent.putExtra(Const.COMMENT_TYPE, Const.BUSPOST_COMMENT);
                    ctx.startActivity(intent);
                }
            });

            editIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Pass a byte array to the next activity so we can convert it
                    // to a bitmap. Passing a whole bitmap is too large
                    final byte[] imgBlob = list.get(getAdapterPosition()).getBlobPhoto();

                    Intent intent = new Intent(ctx, EditBusinessPostActivity.class);
                    intent.putExtras(extras);
                    intent.putExtra("POST_TEXT", list.get(getAdapterPosition()).getPostTxt());
                    intent.putExtra(Const.BUS_POST_BITMAP, imgBlob);
                    intent.putExtra("POST_ID", list.get(getAdapterPosition()).getPostId());

                    ctx.startActivity(intent);
                }
            });

            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BusinessServiceLogic businessServiceLogic = new BusinessServiceLogic();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                    builder.setMessage("Are you sure you want to delete this post?")
                            .setCancelable(false)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        businessServiceLogic.deleteBusinessPost(list.get(getAdapterPosition()).getPostId(),
                                                new businessStringResponse() {
                                                    @Override
                                                    public void onSuccess(String s) {
                                                        Toast.makeText(ctx,
                                                                "Successfully Deleted Post!", Toast.LENGTH_LONG).show();

                                                        final Handler handler = new Handler();


                                                        Intent intent = new Intent(ctx, ctx.getClass());
                                                        intent.putExtras(extras);

                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                ctx.startActivity(intent);
                                                            }
                                                        }, 800);
                                                    }

                                                    @Override
                                                    public void onError(String s) {
                                                        Log.d("DELETE REVIEW ERROR", s);
                                                        Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                        );
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

        }
    }



}
