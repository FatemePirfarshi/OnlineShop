package com.example.onlineshop.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.remote.NetworkParams;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstance;
import com.example.onlineshop.data.remote.retrofit.WoocommerceService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    public static final String TAG = "ProductRepository";
    private static ProductRepository sInstance;
    private WoocommerceService mWoocommerceService;
    private MutableLiveData<List<ProductItem>> mListLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ProductItem>> getListLiveData() {
        return mListLiveData;
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
        Call<List<ProductItem>> call =
                mWoocommerceService.listProductItems(NetworkParams.getProductsOptions());
        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                List<ProductItem> items = response.body();
                mListLiveData.setValue(items);
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
