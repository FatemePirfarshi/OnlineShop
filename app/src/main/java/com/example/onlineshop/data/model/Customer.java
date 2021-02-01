package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("first_name")
    private String mFirstName;

    @SerializedName("last_name")
    private String mLastName;

    @SerializedName("username")
    private String mUserName;

    @SerializedName("billing")
    private Billing mBilling;

    @SerializedName("shipping")
    private Shipping mShipping;

    public String getEmail() {
        return mEmail;
    }

    public Customer(String email){
        mEmail = email;
    }

    public Customer(String email, String userName){
        mEmail = email;
        mUserName = userName;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public Billing getBilling() {
        return mBilling;
    }

    public void setBilling(Billing billing) {
        mBilling = billing;
    }

    public Shipping getShipping() {
        return mShipping;
    }

    public void setShipping(Shipping shipping) {
        mShipping = shipping;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Integer getId() {
        return mId;
    }
}

