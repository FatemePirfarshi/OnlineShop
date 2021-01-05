package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.remote.NetworkParams;
import com.example.onlineshop.data.repository.ProductRepository;

import java.util.List;

public class HomeViewModel extends ProductViewModel {

    private ProductRepository mRepository;
    private final LiveData<Integer> mPerPage;
    private final LiveData<List<ProductItem>> mPopularItemsLiveData;
    private final LiveData<List<ProductItem>> mRecentItemsLiveData;
    private final LiveData<List<ProductItem>> mTopItemsLiveData;

    private MutableLiveData<ProductItem> mClickedProductItem;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance();
        mPerPage = mRepository.getPerPage();
        mPopularItemsLiveData = mRepository.getPopularItemsLiveData();
        mRecentItemsLiveData = mRepository.getRecentItemsLiveData();
        mTopItemsLiveData = mRepository.getTopItemsLiveData();
    }

    public LiveData<Integer> getPerPage() {
        return mPerPage;
    }

    public LiveData<List<ProductItem>> getPopularItemsLiveData() {
        return mPopularItemsLiveData;
    }

    public LiveData<List<ProductItem>> getRecentItemsLiveData() {
        return mRecentItemsLiveData;
    }

    public LiveData<List<ProductItem>> getTopItemsLiveData() {
        return mTopItemsLiveData;
    }

//    public MutableLiveData<ProductItem> getClickedProductItem() {
//        mClickedProductItem = new MutableLiveData<>();
//        return mClickedProductItem;
//    }

    public void fetchTotalProducts() {
        mRepository.fetchTotalProducts();
    }

    public void fetchPopularItems(int perPage) {
        mRepository.fetchPopularItems(perPage);
    }

    public void fetchRecentItems(int perPage) {
        mRepository.fetchRecentItems(perPage);
    }

    public void fetchTopItems(int perPage) {
        mRepository.fetchTopItems(perPage);
    }

//    public void onProductClicked(String listPosition, int position) {
//        ProductItem item;
//        switch (listPosition) {
//            case NetworkParams.POPULAR:
//                item = mPopularItemsLiveData.getValue().get(position);
//                break;
//            case NetworkParams.RECENT:
//                item = mRecentItemsLiveData.getValue().get(position);
//                break;
//            default:
//                item = mTopItemsLiveData.getValue().get(position);
//                break;
//        }
//        mClickedProductItem.setValue(item);
//    }

    public void navigateToDetail() {
        //todo
    }
}
