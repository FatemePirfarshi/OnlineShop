package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("reviewer")
    private String mReviewer;

    @SerializedName("reviewer_email")
    private String mReviewerEmail;

    @SerializedName("review")
    private String mReview;

    @SerializedName("product_id")
    private int mProductId;

    @SerializedName("rating")
    private int mRating;

    @SerializedName("date_created")
    private String mDateCreated;

    public Review(String reviewer, String reviewerEmail, int rating, String review, int productId) {
        mReviewer = reviewer;
        mReviewerEmail = reviewerEmail;
        mReview = review;
        mRating = rating;
        mProductId = productId;
    }

    public Review(String reviewer, int rating, String review, String date){
        mReviewer = reviewer;
        mRating = rating;
        mReview = review;
        mDateCreated = date;
    }

    public String getReviewer() {
        return mReviewer;
    }

    public void setReviewer(String reviewer) {
        mReviewer = reviewer;
    }

    public String getReviewerEmail() {
        return mReviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        mReviewerEmail = reviewerEmail;
    }

    public String getReview() {
        return mReview;
    }

    public void setReview(String review) {
        mReview = review;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }
}
