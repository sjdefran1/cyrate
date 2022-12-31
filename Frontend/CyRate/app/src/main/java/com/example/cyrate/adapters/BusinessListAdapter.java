package com.example.cyrate.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyrate.ImageLoaderTask;
import com.example.cyrate.R;
import com.example.cyrate.models.BusinessRecyclerViewInterface;
import com.example.cyrate.models.BusinessListCardModel;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter to populate the RecyclerView for a list of Businesses.
 */
public class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.MyViewHolder> {
    private final BusinessRecyclerViewInterface recyclerViewInterface;
    Context ctx;
    ArrayList<BusinessListCardModel> businessCardList;

    public BusinessListAdapter(
            Context ctx,
            ArrayList<BusinessListCardModel> businessCardList,
            BusinessRecyclerViewInterface recyclerViewInterface
            ){
        this.ctx = ctx;
        this.businessCardList = businessCardList;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    // This is where we inflate our layout (RestaurantListCard) for each of our rows in the view
    @NonNull
    @Override
    public BusinessListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.business_list_card, parent, false);
        return new BusinessListAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    // Since this is a recycle view, cards will be discarded when they go off screen.
    // However, when a card goes off screen, another will come on screen.
    // The new card will be 'binded' basically just updating the data for our
    // RestaurantListCardModel (name, address, etc..)
    @Override
    public void onBindViewHolder(@NonNull BusinessListAdapter.MyViewHolder holder, int position) {
        String[] hours = businessCardList.get(position).getHours().split("\\|");
        Log.d("HOURS LIST", hours[0]);

        int totalReviews = businessCardList.get(position).getReviewCount();
        int reviewSum = businessCardList.get(position).getReviewSum();

        float rating = (float) reviewSum / totalReviews;

        if(totalReviews == 0){
            holder.restRating.setText("n/a");
        }
        else{
            holder.restRating.setText(String.format("%.1f", rating));
        }

        new ImageLoaderTask(businessCardList.get(position).getPhotoUrl(), holder.restImg).execute();
        holder.restName.setText(businessCardList.get(position).getBusName());
        holder.restAddress.setText(businessCardList.get(position).getLocation());
        holder.restCategory.setText(businessCardList.get(position).getBusType());
        holder.restHours.setText(hours[0]); // Substring 5 since hours[0] is "Mon: <hours>" cut off the Mon: part


    }

    @Override
    public int getItemCount() {
        return businessCardList.size();
    }

    // Class necessary and is similar for having an onCreate method. Allows us to get all our views
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView restImg;
        TextView restName, restCategory, restAddress, restRating, restHours;

        public MyViewHolder(@NonNull View itemView, BusinessRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            restImg = itemView.findViewById(R.id.restaurant_img);
            restName = itemView.findViewById(R.id.restaurant_name);
            restCategory = itemView.findViewById(R.id.restaurant_category);
            restAddress = itemView.findViewById(R.id.restaurant_address);
            restRating = itemView.findViewById(R.id.restaurant_rating_value);
            restHours = itemView.findViewById(R.id.restaurant_hours);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(recyclerViewInterface != null){
                                int pos = getAdapterPosition();

                                if(pos != RecyclerView.NO_POSITION){
                                    recyclerViewInterface.onBusinessClick(pos);
                                }
                            }
                        }
                    }
            );

        }
    }
}
