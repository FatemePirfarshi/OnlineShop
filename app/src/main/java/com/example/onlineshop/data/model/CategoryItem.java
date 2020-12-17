package com.example.onlineshop.data.model;

public class CategoryItem {
    private int mId;
    private String mName;
    private int mCount;
    private String mImage;

    public CategoryItem(int id, String name, int count, String image) {
        mId = id;
        mName = name;
        mCount = count;
        mImage = image;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}
