package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlineshop.data.repository.ProductRepository;

public class HomeViewModel extends AndroidViewModel {

    private ProductRepository mRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
    }
}
