package com.example.onlineshop.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstance;
import com.example.onlineshop.data.remote.retrofit.WoocommerceService;

import java.util.List;

public class FilterRepository {

    private static FilterRepository sInstance;
    private WoocommerceService mWoocommerceServiceProduct;
    private List<ProductItem> mProductItems;

    private MutableLiveData<List<ProductItem>> cheapestItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> mostExpensiveItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> recentItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mPageCount = new MutableLiveData<>();

    public static FilterRepository getInstance(){
        if(sInstance == null)
            sInstance = new FilterRepository();
        return sInstance;
    }

    private FilterRepository(){
        mWoocommerceServiceProduct = RetrofitInstance.getProductInstance().create(WoocommerceService.class);
    }

    public MutableLiveData<List<ProductItem>> getCheapestItemsLiveData() {
        return cheapestItemsLiveData;
    }

    public MutableLiveData<List<ProductItem>> getMostExpensiveItemsLiveData() {
        return mostExpensiveItemsLiveData;
    }

    public MutableLiveData<List<ProductItem>> getRecentItemsLiveData() {
        return recentItemsLiveData;
    }

    public MutableLiveData<Integer> getPageCount() {
        return mPageCount;
    }


}
