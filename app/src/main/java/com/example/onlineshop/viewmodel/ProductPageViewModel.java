package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.ProductRepository;
import com.example.onlineshop.utilities.QueryPreferences;

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

    public void addToCartClicked(){
        ProductItem item = mProductItemLiveData.getValue();
        item.setCountInCart(1);
        QueryPreferences.addCartProduct(getApplication(), item);
        mRepository.setCartItemLiveData(QueryPreferences.getCartProducts(getApplication()));
    }
}
