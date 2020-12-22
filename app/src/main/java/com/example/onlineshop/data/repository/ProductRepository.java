package com.example.onlineshop.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.remote.NetworkParams;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstance;
import com.example.onlineshop.data.remote.retrofit.WoocommerceService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    public static final String TAG = "ProductRepository";
    private static ProductRepository sInstance;
    private WoocommerceService mWoocommerceServiceCategory;
    private WoocommerceService mWoocommerceServiceProduct;

    private List<ProductItem> mProductItems;
    private List<CategoryItem> mCategoryItems = new ArrayList<>();

    private MutableLiveData<List<ProductItem>> mProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CategoryItem>> mCategoriesListLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mPageCount = new MutableLiveData<>();
    private MutableLiveData<Integer> mCategoryItemId = new MutableLiveData<>();

    public MutableLiveData<List<ProductItem>> getProductListLiveData() {
        return mProductListLiveData;
    }

    public MutableLiveData<List<CategoryItem>> getCategoriesListLiveData() {
        return mCategoriesListLiveData;
    }

    public MutableLiveData<Integer> getCategoryItemId() {
        return mCategoryItemId;
    }

    public MutableLiveData<Integer> getPageCount() {
        return mPageCount;
    }

    public static ProductRepository getInstance() {
        if (sInstance == null)
            sInstance = new ProductRepository();
        return sInstance;
    }

    private ProductRepository() {
        mWoocommerceServiceCategory = RetrofitInstance.getCategoryInstance().create(WoocommerceService.class);
        mWoocommerceServiceProduct = RetrofitInstance.getProductInstance().create(WoocommerceService.class);
    }

    public void fetchCategoryItemsAsync(int page) {

        Call<List<CategoryItem>> call = mWoocommerceServiceCategory.listCategoryItems(
                NetworkParams.getCategoryOptions(page));
        call.enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                Headers headerList = response.headers();
                for (int i = 0; i < headerList.size(); i++) {
                    int totalPage = Integer.parseInt(headerList.get("X-WP-TotalPages"));
                    Log.e(TAG, "total page is: " + totalPage);
                    mPageCount.setValue(totalPage);
                }
                List<CategoryItem> items = response.body();
                mCategoryItems.addAll(items);
                mCategoriesListLiveData.setValue(mCategoryItems);
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductItemsAsync(int page, int categoryId) {
        if(page == 1)
            mProductItems = new ArrayList<>();

        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getProductsOptions(page, categoryId));
        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                Headers headerList = response.headers();
                for (int i = 0; i < headerList.size(); i++) {
                    int totalPage = Integer.parseInt(headerList.get("X-WP-TotalPages"));
                    mPageCount.setValue(totalPage);
                }
                List<ProductItem> items = response.body();
                mProductItems.addAll(items);
                mProductListLiveData.setValue(mProductItems);
                mCategoryItemId.setValue(categoryId);
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
//    public List<ProductItem> fetchPopularItems() {
//        return new ArrayList<>();
//    }
//
//    public List<ProductItem> fetchLatestItems() {
//        return new ArrayList<>();
//    }
//
//    public List<ProductItem> fetchMostVisited() {
//        return new ArrayList<>();
//    }
}
