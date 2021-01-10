package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.ProductRepository;

import java.util.List;

public class HomeViewModel extends ProductViewModel {

    private ProductRepository mRepository;
    private final LiveData<Integer> mPerPage;
    private final LiveData<List<ProductItem>> mPopularItemsLiveData;
    private final LiveData<List<ProductItem>> mRecentItemsLiveData;
    private final LiveData<List<ProductItem>> mTopItemsLiveData;
    private MutableLiveData<List<String>> mImagesLiveData = new MutableLiveData<>();

    private MutableLiveData<ProductItem> mClickedProductItem;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance();
        mPerPage = mRepository.getPerPage();
        mPopularItemsLiveData = mRepository.getPopularItemsLiveData();
        mRecentItemsLiveData = mRepository.getRecentItemsLiveData();
        mTopItemsLiveData = mRepository.getTopItemsLiveData();
    }

    public LiveData<Integer> getPerPage() {
        return mPerPage;
    }

    public LiveData<List<ProductItem>> getPopularItemsLiveData() {
        return mPopularItemsLiveData;
    }

    public LiveData<List<ProductItem>> getRecentItemsLiveData() {
        return mRecentItemsLiveData;
    }

    public LiveData<List<ProductItem>> getTopItemsLiveData() {
        return mTopItemsLiveData;
    }

    public MutableLiveData<List<String>> getImagesLiveData() {
        return mImagesLiveData;
    }

    public void fetchTotalProducts() {
        mRepository.fetchTotalProducts();
    }

    public void fetchPopularItems(int perPage) {
        mRepository.fetchPopularItems(perPage);
    }

    public void fetchRecentItems(int perPage) {
        mRepository.fetchRecentItems(perPage);
    }

    public void fetchTopItems(int perPage) {
        mRepository.fetchTopItems(perPage);
    }

    public void fetchRecommendedProducts(){
//        mRepository.fetchProductItemsAsync(2, 119);
        //todo:search this category
    }

    public void navigateToDetail() {
        //todo
    }
}
