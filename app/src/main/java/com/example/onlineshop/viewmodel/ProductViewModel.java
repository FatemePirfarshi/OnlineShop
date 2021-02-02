package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.Review;
import com.example.onlineshop.data.repository.CustomerRepository;
import com.example.onlineshop.data.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mClickedProductItem;
    private ProductRepository mRepository;
    private CustomerRepository mCustomerRepository;

    public MutableLiveData<Integer> getClickedProductItem() {
        mClickedProductItem = new MutableLiveData<>();
        return mClickedProductItem;
    }

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
        mCustomerRepository = CustomerRepository.getInstance();
    }

    public void onProductItemClicked(int id){

        Log.e("productItemClicked", "this id clicked in pvm" + id);
        mRepository.fetchProductItemWithId(id);
        mCustomerRepository.fetchProductReviews(id);
        mClickedProductItem.setValue(id);
    }
}
