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

    public ProductListViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance();
        mProductListLiveData = mRepository.getProductListLiveData();
        mPageCount = mRepository.getPageCount();
        mCategoryItemId = mRepository.getCategoryItemId();
    }

    public void openDrawer() {
        mOpenedLiveData.setValue(true);
        Log.d(TAG, "open drawer from view model called");
    }

    public List<ProductItem> getCurrentItems() {
        return mProductListLiveData.getValue();
    }

    public void fetchProductItems(int page) {
        mRepository.fetchProductItemsAsync(page, mCategoryItemId.getValue());
    }
    //    public void fetchItems(int state){
//        switch (state){
//            case 0:
//                mRepository.fetchLatestItems();
//                break;
//            case 1:
//                mRepository.fetchPopularItems();
//                break;
//            case 2:
//                mRepository.fetchMostVisited();
//                break;
//        }
//    }
}
