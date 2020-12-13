package com.example.onlineshop.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static ProductRepository sInstance;
    private Context mContext;
    private MutableLiveData<List<ProductItem>> mListLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ProductItem>> getCurrentList() {
        return mListLiveData;
    }

    public static ProductRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new ProductRepository(context);
        return sInstance;
    }

    private ProductRepository(Context context) {
        mContext = context.getApplicationContext();
    }

    public List<ProductItem> fetchPopularItems(){
        return new ArrayList<>();
    }
    public List<ProductItem> fetchLatestItems(){
        return new ArrayList<>();
    }
    public List<ProductItem> fetchMostVisited(){
        return new ArrayList<>();
    }
}
