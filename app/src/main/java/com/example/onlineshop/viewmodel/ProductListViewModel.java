package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.ProductRepository;

import java.util.List;

public class ProductListViewModel extends AndroidViewModel {

    public static final String TAG = "ProductListViewModel";
    private ProductRepository mRepository;
    private final LiveData<List<ProductItem>> mProductListLiveData;
    private final LiveData<Integer> mPageCount;
    private final LiveData<Integer> mCategoryItemId;
    private final LiveData<List<ProductItem>> mPopularItemsLiveData;
    private final LiveData<List<ProductItem>> mRecentItemsLiveData;
    private final LiveData<List<ProductItem>> mTopItemsLiveData;
    private final LiveData<Integer> mPerPage;

    private MutableLiveData<Boolean> mOpenedLiveData = new MutableLiveData<>();

    public LiveData<List<ProductItem>> getProductListLiveData() {
        return mProductListLiveData;
    }

    public LiveData<Integer> getPageCount() {
        return mPageCount;
    }

    public LiveData<Boolean> getOpenedLiveData() {
        return mOpenedLiveData;
    }

    public LiveData<Integer> getCategoryItemId() {
        return mCategoryItemId;
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

    public LiveData<Integer> getPerPage() {
        return mPerPage;
    }

    public ProductListViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance();
        mProductListLiveData = mRepository.getProductListLiveData();
        mPageCount = mRepository.getPageCount();
        mCategoryItemId = mRepository.getCategoryItemId();
        mPerPage = mRepository.getPerPage();
        mPopularItemsLiveData = mRepository.getPopularItemsLiveData();
        mRecentItemsLiveData = mRepository.getRecentItemsLiveData();
        mTopItemsLiveData = mRepository.getTopItemsLiveData();
    }

    public void openDrawer() {
        mOpenedLiveData.setValue(true);
        Log.d(TAG, "open drawer from view model called");
    }

    public void fetchProductItems(int page) {
        mRepository.fetchProductItemsAsync(page, mCategoryItemId.getValue());
    }

    public void fetchTotalProducts(){
        mRepository.fetchTotalProducts();
    }

    public void fetchPopularItems(int perPage){
        mRepository.fetchPopularItems(perPage);
    }

    public void fetchRecentItems(int perPage){
        mRepository.fetchRecentItems(perPage);
    }

    public void fetchTopItems(int perPage){
        mRepository.fetchTopItems(perPage);
    }
}
