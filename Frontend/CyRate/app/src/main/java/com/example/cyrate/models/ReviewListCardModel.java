package com.example.cyrate.models;

public class ReviewListCardModel {
    private int reviewId;
    private int rateVal;
    private String reviewText;
    private int businessId;
    private ReviewUserModel reviewUser;
    private String reviewHeader;

    public ReviewListCardModel(int reviewId, int rateVal, String reviewText, String reviewHeader, int businessId, ReviewUserModel reviewUser) {
        this.reviewId = reviewId;
        this.rateVal = rateVal;
        this.reviewText = reviewText;
        this.reviewHeader = reviewHeader;
        this.businessId = businessId;
        this.reviewUser = reviewUser;
    }

    /**
     *
     * @return Header for this review
     */
    public String getReviewHeader() {
        return reviewHeader;
    }

    /**
     *
     * @param reviewHeader
     */
    public void setReviewHeader(String reviewHeader) {
        this.reviewHeader = reviewHeader;
    }

    /**
     *
     * @return Review ID
     */
    public int getReviewId() {
        return reviewId;
    }

    /**
     *
     * @param reviewId
     */
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    /**
     *
     * @return Review rating
     */
    public int getRateVal() {
        return rateVal;
    }

    /**
     *
     * @param rateVal
     */
    public void setRateVal(int rateVal) {
        this.rateVal = rateVal;
    }

    /**
     *
     * @return Review text body
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     *
     * @param reviewText
     */
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    /**
     *
     * @return Business this review was left on
     */
    public int getBusinessId() {
        return businessId;
    }

    /**
     *
     * @param businessId
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    /**
     *
     * @return The user who left the review
     */
    public ReviewUserModel getReviewUser() {
        return reviewUser;
    }

    /**
     *
     * @param reviewUser
     */
    public void setReviewUser(ReviewUserModel reviewUser) {
        this.reviewUser = reviewUser;
    }

    @Override
    public String toString() {
        return "ReviewListCardModel{" +
                "reviewId=" + reviewId +
                ", rateVal=" + rateVal +
                ", reviewText='" + reviewText + '\'' +
                ", businessId=" + businessId +
                ", reviewUserId=" + reviewUser +
                '}';
    }
}