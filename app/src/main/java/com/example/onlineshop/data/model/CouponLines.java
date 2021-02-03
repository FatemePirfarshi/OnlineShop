package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class CouponLines {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("code")
    private String mCode;

    @SerializedName("amount")
    private String mAmount;

    public CouponLines(String code, String amount) {
        mCode = code;
        mAmount = amount;
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

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }
}
