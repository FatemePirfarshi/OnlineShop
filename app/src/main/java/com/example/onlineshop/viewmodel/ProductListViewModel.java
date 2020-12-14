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

    public static final String TAG = "callOpenDrawer";
    private ProductRepository mRepository;
    private final LiveData<List<ProductItem>> mListLiveData;

    private MutableLiveData<Boolean> mOpenedLiveData = new MutableLiveData<>();

    public LiveData<List<ProductItem>> getListLiveData() {
        return mListLiveData;
    }

    public MutableLiveData<Boolean> getOpenedLiveData() {
        return mOpenedLiveData;
    }

    public ProductListViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance();
        mListLiveData = mRepository.getListLiveData();
    }

//    public LiveData<List<ProductItem>> getCurrentList(){
//
//    }

    public void openDrawer() {
        mOpenedLiveData.setValue(true);
        Log.d(TAG, "open drawer from view model called");
    }

    public List<ProductItem> getCurrentItems() {
        return mListLiveData.getValue();
    }

    public void fetchItems() {
        mRepository.fetchItemsAsync();
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
