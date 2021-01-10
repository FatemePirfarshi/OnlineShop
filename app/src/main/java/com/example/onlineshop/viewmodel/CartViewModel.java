package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.ProductRepository;
import com.example.onlineshop.utilities.QueryPreferences;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private LiveData<List<ProductItem>> mCartProductItem;

    public CartViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
        mCartProductItem = mRepository.getCartItemLiveData();
    }

    public LiveData<List<ProductItem>> getCartProductItem() {
        return mCartProductItem;
    }

    public void deleteFromCart(ProductItem item){
        QueryPreferences.removeCartProduct(getApplication(), item);
    }

    public void addAgain(ProductItem item){
        QueryPreferences.addCartProduct(getApplication(), item);
    }

    public String setTotalPrice() {
        int total = 0;
        List<ProductItem> items = QueryPreferences.getCartProducts(getApplication());
        for (int i = 0; i < items.size(); i++) {
            total += Integer.parseInt(items.get(i).getProductPrice());
        }
        return total + " تومان ";
    }

//    public void getCartItems(){
//        List<ProductItem> cartItems = QueryPreferences.getCartProducts(getApplication());
//    }
}
