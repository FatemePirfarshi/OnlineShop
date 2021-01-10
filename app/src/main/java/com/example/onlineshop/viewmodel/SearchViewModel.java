package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlineshop.data.repository.ProductRepository;

public class SearchViewModel extends AndroidViewModel {

    private ProductRepository mRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
    }

    public void fetchSearchItems(String query){
        mRepository.fetchSearchItems(query);
    }
}
