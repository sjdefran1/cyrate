package com.example.cyrate.Logic;

import android.annotation.SuppressLint;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.cyrate.AppController;
import com.example.cyrate.Logic.BusinessInterfaces.businessStringResponse;
import com.example.cyrate.Logic.BusinessInterfaces.getBusinessByIDResponse;
import com.example.cyrate.Logic.ReviewInterfaces.getReviewsResponse;
import com.example.cyrate.Logic.ReviewInterfaces.reviewStringResponse;
import com.example.cyrate.activities.MainActivity;
import com.example.cyrate.models.BusinessListCardModel;
import com.example.cyrate.models.ReviewListCardModel;
import com.example.cyrate.models.ReviewUserModel;
import com.example.cyrate.models.UserModel;
import com.example.cyrate.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReviewServiceLogic {

    /**
     * Makes a request to the server to GET a list of all reviews for a
     * specific business.
     *
     * @param busId
     * @param r
     */
    public void getReviews(int busId, getReviewsResponse r) {
        List<ReviewListCardModel> reviewModelsList = new ArrayList<>();
        String url = Const.GET_REVIEWS_BY_BUS_ID + String.valueOf(busId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("REVIEWS", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject review = (JSONObject) response.get(i);
                        Log.d("REVIEW JSON OBJ", review.toString());

                        JSONObject reviewUserJSON = review.getJSONObject("user");
                        ReviewUserModel reviewUserModel = new ReviewUserModel(
                                reviewUserJSON.getInt("userID"),
                                reviewUserJSON.getString("realName"),
                                reviewUserJSON.getString("username"),
                                reviewUserJSON.getString("photoUrl")
                        );

                        ReviewListCardModel reviewListCardModel = new ReviewListCardModel(
                                review.getInt("rid"),
                                review.getInt("rateVal"),
                                review.getString("reviewTxt"),
                                review.getString("reviewHeader"),
                                review.getJSONObject("business").getInt("busId"),
                                reviewUserModel
                        );

                        reviewModelsList.add(reviewListCardModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                r.onSuccess(reviewModelsList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                r.onError(error.toString());
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * Makes a request to the server to GET all the reviews made by a specific User.
     * @param uid
     * @param r
     */
    public void getReviewsByUser(int uid, getReviewsResponse r) {
        List<ReviewListCardModel> reviewModelsList = new ArrayList<>();
        String url = Const.GET_REVIEWS_BY_USER_ID + String.valueOf(uid);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("REVIEWS", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject review = (JSONObject) response.get(i);
                        Log.d("REVIEW JSON OBJ", review.toString());

                        JSONObject reviewUserJSON = review.getJSONObject("user");
                        ReviewUserModel reviewUserModel = new ReviewUserModel(
                                reviewUserJSON.getInt("userID"),
                                reviewUserJSON.getString("realName"),
                                reviewUserJSON.getString("username"),
                                reviewUserJSON.getString("photoUrl")
                        );

                        ReviewListCardModel reviewListCardModel = new ReviewListCardModel(
                                review.getInt("rid"),
                                review.getInt("rateVal"),
                                review.getString("reviewTxt"),
                                review.getString("reviewHeader"),
                                review.getJSONObject("business").getInt("busId"),
                                reviewUserModel
                        );

                        reviewModelsList.add(reviewListCardModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                r.onSuccess(reviewModelsList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                r.onError(error.toString());
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * Makes a request to the server to POST a new review by a User to a specific Business.
     *
     * @param busId
     * @param userId
     * @param reviewTxt
     * @param reviewHeading
     * @param ratingVal
     * @param r
     * @throws JSONException
     */
    public void addReview(int busId, int userId, String reviewTxt, String reviewHeading, int ratingVal, reviewStringResponse r) throws JSONException {
        String url = "http://coms-309-020.class.las.iastate.edu:8080/review/" + String.valueOf(busId) + "/user/" + String.valueOf(userId) + "/createReview";

        Log.d("ADD REVIEW URL", url);

        HashMap<String, Object> params = new HashMap<>();
        params.put("reviewTxt", reviewTxt);
        params.put("reviewHeader", reviewHeading);
        params.put("rateVal", ratingVal);

        Log.d("addReview - newReview", params.toString());


        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        r.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ADD REVIEW ERROR", error.toString());
                        r.onError(error.getMessage());
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * Makes a request to the server to DELETE a specific review.
     *
     * @param reviewId
     * @param r
     * @throws JSONException
     */
    public void deleteReview(int reviewId, reviewStringResponse r) throws JSONException {
        String url = Const.DELETE_REVIEW_BY_ID + String.valueOf(reviewId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                r.onSuccess("Review Deleted");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                r.onError(error.toString());
            }
        }

        );

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * Makes a request to the server to update a specific Review given the updated fields.
     *
     * @param reviewId
     * @param reviewTxt
     * @param reviewHeading
     * @param ratingVal
     * @param r
     * @throws JSONException
     */
    public void editReview(int reviewId, String reviewTxt, String reviewHeading, int ratingVal, reviewStringResponse r) throws JSONException {
        String url = Const.EDIT_REVIEW_BY_ID + String.valueOf(reviewId);

        Log.d("EDIT REVIEW URL", url);

        HashMap<String, Object> params = new HashMap<>();
        params.put("reviewTxt", reviewTxt);
        params.put("reviewHeader", reviewHeading);
        params.put("rateVal", ratingVal);

        Log.d("addReview - newReview", params.toString());


        StringRequest request = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        r.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("EDIT REVIEW ERROR", error.toString());
                        r.onError(error.getMessage());
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }


}
