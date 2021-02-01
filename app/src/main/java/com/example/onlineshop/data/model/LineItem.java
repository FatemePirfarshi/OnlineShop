package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class LineItem {

    @SerializedName("quantity")
    private Integer mQuantity;

    @SerializedName("product_id")
    private Integer mProductId;

    public LineItem(Integer productId, Integer quantity){
        mProductId = productId;
        mQuantity = quantity;
    }

    public Integer getQuantity() {
        return mQuantity;
    }

    public void setQuantity(Integer quantity) {
        mQuantity = quantity;
    }

    public Integer getProductId() {
        return mProductId;
    }

    public void setProductId(Integer productId) {
        mProductId = productId;
    }
}
