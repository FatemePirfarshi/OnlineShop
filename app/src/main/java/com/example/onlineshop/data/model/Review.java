package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("reviewer")
    private String reviewer;

    @SerializedName("reviewer_email")
    private String reviewerEmail;

    @SerializedName("review")
    private String review;

    @SerializedName("product_id")
    private int productId;

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
