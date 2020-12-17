package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.data.repository.ProductRepository;

import java.util.List;

public class CategoryListViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private final MutableLiveData<List<CategoryItem>> mListLiveData;
    private final LiveData<Integer> mCountLiveData;

    public MutableLiveData<List<CategoryItem>> getListLiveData() {
        return mListLiveData;
    }

    public LiveData<Integer> getCountLiveData() {
        return mCountLiveData;
    }

    public CategoryListViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance();
        mListLiveData = mRepository.getCategoriesListLiveData();
        mCountLiveData = mRepository.getPageCount();
    }

    public void fetchCategoryItemsAsync(int page) {
        mRepository.fetchCategoryItemsAsync(page);
    }

    public List<CategoryItem> getCurrentCategories() {
        return mListLiveData.getValue();
    }
}
