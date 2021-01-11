package com.example.onlineshop.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.ProductRepository;

import java.util.List;

public class SortListViewModel extends ViewModel {

    private ProductRepository mRepository;
    private LiveData<List<ProductItem>> mCheapestItem;
    private final LiveData<Integer> mPerPage;
    private final LiveData<Integer> mCategoryItemId;
    private MutableLiveData<Boolean> dismissDialog = new MutableLiveData<>();

    public SortListViewModel() {
        mRepository = ProductRepository.getInstance();
        mCheapestItem = mRepository.getProductListLiveData();
        mCategoryItemId = mRepository.getCategoryItemId();
        mPerPage = mRepository.getPerPage();
    }

    public LiveData<List<ProductItem>> getCheapestItem() {
        return mCheapestItem;
    }

    public MutableLiveData<Boolean> getDismissDialog() {
        return dismissDialog;
    }

    public void fetchTotalProductsForCategory(){
        mRepository.fetchTotalProductsForCategory(mCategoryItemId.getValue());
    }

    public void cheapestClicked() {
        mRepository.fetchCheapestProducts(mPerPage.getValue(), mCategoryItemId.getValue());
        dismissDialog.setValue(true);
    }

    public void mostExpensiveClicked(){
        mRepository.fetchMostExpensiveProducts(mPerPage.getValue(), mCategoryItemId.getValue());
        dismissDialog.setValue(true);
    }

    public void newestClicked(){
        mRepository.fetchNewestProducts(mPerPage.getValue(), mCategoryItemId.getValue());
        dismissDialog.setValue(true);
    }

    public void bestSellersClicked(){
        mRepository.fetchBestSellersProducts(mPerPage.getValue(), mCategoryItemId.getValue());
        dismissDialog.setValue(true);
    }
}
