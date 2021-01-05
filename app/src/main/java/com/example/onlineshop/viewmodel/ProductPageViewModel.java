package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductPageViewModel extends ProductViewModel {

    private final LiveData<ProductItem> mProductItemLiveData;
    private final LiveData<List<ProductItem>> mRelatedItemsLiveData;
    private ProductRepository mRepository;

    public ProductPageViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
        mProductItemLiveData = mRepository.getProductItemLiveData();
        mRelatedItemsLiveData = mRepository.getRelatedItemsLiveData();
    }

    public LiveData<ProductItem> getProductItemLiveData() {
        return mProductItemLiveData;
    }

    public LiveData<List<ProductItem>> getRelatedItemsLiveData() {
        return mRelatedItemsLiveData;
    }

    public void fetchRelatedItems(List<Integer> relatedProductsId){
        mRepository.fetchRelatedItems(relatedProductsId);
    }

//    public void fetchProductItemWithId(int id) {
//        Log.e("productItemClicked", "this id clicked in ppvm " + id);
//        mRepository.fetchProductItemWithId(id);
////        mProductItem = getProductItemLiveData().getValue();
//    }
}
