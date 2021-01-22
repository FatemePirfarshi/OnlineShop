package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.ProductRepository;

import java.util.List;

public class SearchViewModel extends ProductViewModel {

    private ProductRepository mRepository;
    private LiveData<List<ProductItem>> mSearchLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
        mSearchLiveData = mRepository.getSearchItemsLiveData();
    }

    public void fetchSearchItems(String query){
        mRepository.fetchSearchItems(query);
    }

    public LiveData<List<ProductItem>> getSearchLiveData() {
        return mSearchLiveData;
    }
}
