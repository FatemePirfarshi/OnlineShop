package com.example.onlineshop.data.model;

import java.util.Date;
import java.util.List;

public class ProductItem {
    private int mId;
    private String mProductName;
    private String mProductPrice;
    private String mUrl;
    private Date mDateCreated;
    private int mRate;
    private List<String> mImages;
    private List<Integer> mCategoriesId;

    public ProductItem(int id, String productName, String productPrice, String url, Date dateCreated,
                       int rate, List<String> images, List<Integer> categoriesId) {
        mId = id;
        mProductName = productName;
        mProductPrice = productPrice;
        mUrl = url;
        mDateCreated = dateCreated;
        mRate = rate;
        mImages = images;
        mCategoriesId = categoriesId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(String productPrice) {
        mProductPrice = productPrice;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Date getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        mDateCreated = dateCreated;
    }

    public int getRate() {
        return mRate;
    }

    public void setRate(int rate) {
        mRate = rate;
    }

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> images) {
        mImages = images;
    }

    public List<Integer> getCategoriesId() {
        return mCategoriesId;
    }

    public void setCategoriesId(List<Integer> categoriesId) {
        mCategoriesId = categoriesId;
    }
}
