package com.example.onlineshop.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProductItem implements Serializable {
    private int mId;
    private String mProductName;
    private String mProductPrice;
    private String mUrl;
    private Date mDateCreated;
    private int mRate;
    private String mDescription;
    private List<String> mImages;
    private List<Integer> mCategoriesId;
    private List<Integer> mRelatedIds;

    public ProductItem(int id, String productName, String productPrice, String url, Date dateCreated,
                       int rate, String description, List<String> images, List<Integer> categoriesId,
                       List<Integer> relatedIds) {
        mId = id;
        mProductName = productName;
        mProductPrice = productPrice;
        mUrl = url;
        mDateCreated = dateCreated;
        mRate = rate;
        mImages = images;
        mCategoriesId = categoriesId;
        mDescription = description;
        mRelatedIds = relatedIds;
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<Integer> getRelatedIds() {
        return mRelatedIds;
    }

    public void setRelatedIds(List<Integer> relatedIds) {
        mRelatedIds = relatedIds;
    }
}
