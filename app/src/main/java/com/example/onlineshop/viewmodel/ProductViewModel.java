package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.repository.ProductRepository;

public class ProductViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mClickedProductItem;
    private ProductRepository mRepository;

    public MutableLiveData<Integer> getClickedProductItem() {
        mClickedProductItem = new MutableLiveData<>();
        return mClickedProductItem;
    }

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
    }

    public void onProductItemClicked(int id){
        Log.e("productItemClicked", "this id clicked in pvm" + id);
        mRepository.fetchProductItemWithId(id);
        mClickedProductItem.setValue(id);
    }
}
