package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("total")
    private String mTotal;

    @SerializedName("customer_id")
    private int mCustomerId;

    @SerializedName("billing")
    private Billing mBilling;

    @SerializedName("shipping")
    private Shipping mShipping;

    @SerializedName("line_items")
    private List<LineItem> mLineItems;

    public Order(int customerId, List<LineItem> items){
        mCustomerId = customerId;
        mLineItems = items;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public void setCustomerId(int customerId) {
        mCustomerId = customerId;
    }

    public int getCustomerId() {
        return mCustomerId;
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

    public List<LineItem> getLineItems() {
        return mLineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        mLineItems = lineItems;
    }
}
