package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class CouponLines {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("code")
    private String mCode;

    @SerializedName("discount")
    private String mDiscount;

    @SerializedName("discount_tax")
    private String mDiscountTax;

    public CouponLines(String code, String discount) {
        mCode = code;
        mDiscount = discount;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getDiscount() {
        return mDiscount;
    }

    public void setDiscount(String discount) {
        mDiscount = discount;
    }

    public String getDiscountTax() {
        return mDiscountTax;
    }

    public void setDiscountTax(String discountTax) {
        mDiscountTax = discountTax;
    }
}
