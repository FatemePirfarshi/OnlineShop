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
    private WoocommerceService mWoocommerceService;
    private int totalPage;
    private int currentPage = 1;
    private boolean readNumberOfPages = false;
    private List<ProductItem> mProductItems = new ArrayList<>();
    private List<CategoryItem> mCategoryItems = new ArrayList<>();

    private MutableLiveData<List<ProductItem>> mListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CategoryItem>> mCategoriesListLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mPageCount = new MutableLiveData<>();

    public MutableLiveData<List<ProductItem>> getListLiveData() {
        return mListLiveData;
    }

    public MutableLiveData<List<CategoryItem>> getCategoriesListLiveData() {
        return mCategoriesListLiveData;
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
        mWoocommerceService = RetrofitInstance.getInstance().create(WoocommerceService.class);
    }

//    public List<ProductItem> fetchPopularItems(){
////        String pages = mWoocommerceService.getPages("");
//
//        Call<List<ProductItem>> call =
//                mWoocommerceService.listProductItems(NetworkParams.PRODUCT_OPTIONS);
//        try {
//            Response<List<ProductItem>> response = call.execute();
//            Log.e(TAG, response.headers().toString());
//            return response.body();
//        } catch (IOException e) {
//           Log.e(TAG, e.getMessage(), e);
//           return null;
//        }
//    }

    public void fetchItemsAsync() {

        Call<List<ProductItem>> call = mWoocommerceService.listProductItems(
                NetworkParams.getPageOptions(),
                NetworkParams.getProductsOptions(currentPage));

        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                List<ProductItem> items = response.body();
                currentPage++;
                Log.e(TAG, "current page is: " + currentPage);
                if (!readNumberOfPages) {
                    Headers headerList = response.headers();
                    for (int i = 0; i < headerList.size(); i++) {
                        totalPage = Integer.parseInt(headerList.get("X-WP-TotalPages"));
                        Log.e(TAG, "total page is: " + totalPage);
                    }
                    readNumberOfPages = true;
                }

                mProductItems.addAll(items);
                mListLiveData.setValue(mProductItems);
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCategoryItemsAsync(int page) {

        Call<List<CategoryItem>> call = mWoocommerceService.listCategoryItems(
                NetworkParams.getCategoryOptions(page));
        call.enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                Headers headerList = response.headers();
                for (int i = 0; i < headerList.size(); i++) {
                    totalPage = Integer.parseInt(headerList.get("X-WP-TotalPages"));
                    Log.e(TAG, "total page is: " + totalPage);
                }
                mPageCount.setValue(totalPage);

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
