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
    private final LiveData<List<CategoryItem>> mListLiveData;
    private final LiveData<Integer> mPageCount;
    private MutableLiveData<Boolean> mNavigateLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getNavigateLiveData() {
        return mNavigateLiveData;
    }

    public LiveData<List<CategoryItem>> getListLiveData() {
        return mListLiveData;
    }

    public LiveData<Integer> getPageCount() {
        return mPageCount;
    }

    public CategoryListViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance();
        mListLiveData = mRepository.getCategoriesListLiveData();
        mPageCount = mRepository.getPageCount();
    }

    public void fetchCategoryItemsAsync(int page) {
        mRepository.fetchCategoryItemsAsync(page);
    }

    public List<CategoryItem> getCurrentCategories() {
        return mListLiveData.getValue();
    }

    public void onClickListItem(int position) {
        CategoryItem item = mListLiveData.getValue().get(position);
        mRepository.fetchProductItemsAsync(1, item.getId());
        mNavigateLiveData.setValue(true);
    }
}
