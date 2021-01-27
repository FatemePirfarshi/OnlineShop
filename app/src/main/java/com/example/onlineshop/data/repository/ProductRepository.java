package com.example.onlineshop.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.data.model.Customer;
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
    private WoocommerceService mWoocommerceServiceProductWithId;
    private WoocommerceService mWoocommerceServiceCustomer;

    private List<ProductItem> mProductItems;
    private List<ProductItem> relatedItems;
    private List<CategoryItem> mCategoryItems = new ArrayList<>();
//    private ProductItem mProductItem;

    private MutableLiveData<List<ProductItem>> mProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CategoryItem>> mCategoriesListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> mPopularItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> mRecentItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> mTopItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> mSearchItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String>> mOfferPicsLiveData = new MutableLiveData<>();

    private MutableLiveData<Integer> mPageCount = new MutableLiveData<>();
    private MutableLiveData<Integer> mCategoryItemId = new MutableLiveData<>();
    private MutableLiveData<Integer> mPerPage = new MutableLiveData<>();

    private MutableLiveData<ProductItem> mProductItemLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> mRelatedItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> mCartItemLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ProductItem>> getCartItemLiveData() {
        return mCartItemLiveData;
    }

    public void setCartItemLiveData(List<ProductItem> cartItemLiveData) {
        mCartItemLiveData.setValue(cartItemLiveData);
    }

    public MutableLiveData<List<String>> getOfferPicsLiveData() {
        return mOfferPicsLiveData;
    }

    public MutableLiveData<List<ProductItem>> getRelatedItemsLiveData() {
        return mRelatedItemsLiveData;
    }

    public MutableLiveData<List<ProductItem>> getProductListLiveData() {
        return mProductListLiveData;
    }

    public MutableLiveData<List<CategoryItem>> getCategoriesListLiveData() {
        return mCategoriesListLiveData;
    }

    public MutableLiveData<List<ProductItem>> getPopularItemsLiveData() {
        return mPopularItemsLiveData;
    }

    public MutableLiveData<List<ProductItem>> getRecentItemsLiveData() {
        return mRecentItemsLiveData;
    }

    public MutableLiveData<List<ProductItem>> getTopItemsLiveData() {
        return mTopItemsLiveData;
    }

    public MutableLiveData<List<ProductItem>> getSearchItemsLiveData() {
        return mSearchItemsLiveData;
    }

    public MutableLiveData<Integer> getCategoryItemId() {
        return mCategoryItemId;
    }

    public MutableLiveData<Integer> getPageCount() {
        return mPageCount;
    }

    public MutableLiveData<Integer> getPerPage() {
        return mPerPage;
    }

    public MutableLiveData<ProductItem> getProductItemLiveData() {
        return mProductItemLiveData;
    }

    public static ProductRepository getInstance() {
        if (sInstance == null) {
            sInstance = new ProductRepository();
//            sInstance.fetchTotalProducts();
        }
        return sInstance;
    }

    private ProductRepository() {
        mWoocommerceServiceCategory = RetrofitInstance.getCategoryInstance().create(WoocommerceService.class);
        mWoocommerceServiceProduct = RetrofitInstance.getProductInstance().create(WoocommerceService.class);
        mWoocommerceServiceProductWithId = RetrofitInstance.getProductInstanceWithId().create(WoocommerceService.class);
        mWoocommerceServiceCustomer = RetrofitInstance.getCustomerInstance().create(WoocommerceService.class);
    }

    public void fetchCategoryItemsAsync(int page) {
        mCategoriesListLiveData.setValue(new ArrayList<>());

        Call<List<CategoryItem>> call = mWoocommerceServiceCategory.listCategoryItems(
                NetworkParams.getCategoryOptions(page));
        call.enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                Headers headerList = response.headers();
                for (int i = 0; i < headerList.size(); i++) {
                    int totalPage = Integer.parseInt(headerList.get("X-WP-TotalPages"));
                    Log.e(TAG, "total page is: " + totalPage);
//                    int total = Integer.parseInt(headerList.get("X-WP-Total"));
//                    Log.e(TAG, "total from category is: " + total);

                    mPageCount.setValue(totalPage);
                }
                List<CategoryItem> items = response.body();
                mCategoryItems.addAll(items);
                for (int i = 0; i < mCategoryItems.size(); i++) {
                    Log.e(TAG, "this category id is: " + mCategoryItems.get(i).getId());
                }
                mCategoriesListLiveData.setValue(mCategoryItems);
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductItemsAsync(int page, int categoryId) {
        if (page == 1) {
            mProductItems = new ArrayList<>();
            mProductListLiveData.setValue(mProductItems);
        }

        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getProductsOptions(page, categoryId));
        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                Headers headerList = response.headers();
                for (int i = 0; i < headerList.size(); i++) {
                    int totalPage = Integer.parseInt(headerList.get("X-WP-TotalPages"));
//                    int total = Integer.parseInt(headerList.get("X-WP-Total"));
                    Log.e(TAG, "the total products" + totalPage);
//                    Log.e(TAG, "total from products is: " + total);
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

    public void fetchTotalProducts() {
        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getBaseOptions());
        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                Headers headerList = response.headers();
                for (int i = 0; i < headerList.size(); i++) {
                    int perPage = Integer.parseInt(headerList.get("X-WP-Total"));
                    mPerPage.setValue(perPage);
                }
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchTotalProductsForCategory(int categoryId) {
        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getPerPageForCategory(categoryId));
        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                Headers headerList = response.headers();
                for (int i = 0; i < headerList.size(); i++) {
                    int perPage = Integer.parseInt(headerList.get("X-WP-Total"));
                    mPerPage.setValue(perPage);
                }
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchPopularItems(int perPage) {

        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getHomeProductOptions(perPage, NetworkParams.POPULAR));
        call.enqueue(getItemsCallback(mPopularItemsLiveData));
    }

    public void fetchRecentItems(int perPage) {

        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getHomeProductOptions(perPage, NetworkParams.RECENT));
        call.enqueue(getItemsCallback(mRecentItemsLiveData));
    }

    public void fetchTopItems(int perPage) {

        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getHomeProductOptions(perPage, NetworkParams.TOP));
        call.enqueue(getItemsCallback(mTopItemsLiveData));
    }

    private Callback<List<ProductItem>> getItemsCallback(MutableLiveData<List<ProductItem>> itemsLiveData) {
        itemsLiveData.setValue(new ArrayList<>());

        return new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                itemsLiveData.setValue(response.body());
//                Log.e(TAG, "item image is : " + response.body().get(0).getImages().get(0));
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };
    }

    public void fetchProductItemWithId(int id) {

        Call<ProductItem> call = mWoocommerceServiceProductWithId.getProductItem(
                id, NetworkParams.getBaseOptions());
        call.enqueue(new Callback<ProductItem>() {
            @Override
            public void onResponse(Call<ProductItem> call, Response<ProductItem> response) {
                ProductItem mProductItem = response.body();
                mProductItemLiveData.setValue(mProductItem);
                fetchRelatedItems(mProductItem.getRelatedIds());
                Log.e(TAG, "this item Clicked: " + response.body().getProductName());
            }

            @Override
            public void onFailure(Call<ProductItem> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
//        fetchRelatedItems(mProductItem.getRelatedIds());
    }

    public void fetchRelatedItems(List<Integer> relatedProductsId) {

        relatedItems = new ArrayList<>();

        for (int i = 0; i < relatedProductsId.size(); i++) {
            Call<ProductItem> call = mWoocommerceServiceProductWithId.getProductItem(
                    relatedProductsId.get(i), NetworkParams.getBaseOptions());
            call.enqueue(new Callback<ProductItem>() {
                @Override
                public void onResponse(Call<ProductItem> call, Response<ProductItem> response) {
                    relatedItems.add(response.body());
                    mRelatedItemsLiveData.setValue(relatedItems);

//                    Log.e("RelatedItems", "related item is: " + response.body().getProductName());
                }
                @Override
                public void onFailure(Call<ProductItem> call, Throwable t) {
                    Log.e(TAG, t.getMessage(), t);
                }
            });
        }

        for (ProductItem item : relatedItems) {
            Log.e("RelatedItems", "related item is: " + item.getProductName());
        }
    }

    public void fetchSearchItems(String query) {
        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getSearchOptions(query, mCategoryItemId.getValue()));
        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
//                mSearchItemsLiveData.setValue(response.body());
                mProductListLiveData.setValue(response.body());
//                String res = response.body().get(0).getProductName();
//                Log.e("SearchActivity", res);
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchOfferPics() {
        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getSearchOptions("تخفیفات", 119));
        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                mOfferPicsLiveData.setValue(response.body().get(0).getImages());
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCheapestProducts(int perPage, int categoryId) {

        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getCheapest(perPage, categoryId));
        call.enqueue(getSortList(categoryId));
    }

    public void fetchMostExpensiveProducts(int perPage, int categoryId) {
        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getMostExpensive(perPage, categoryId, NetworkParams.PRICE));
        call.enqueue(getSortList(categoryId));
    }

    public void fetchNewestProducts(int perPage, int categoryId) {
        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getMostExpensive(perPage, categoryId, NetworkParams.RECENT));
        call.enqueue(getSortList(categoryId));
    }

    public void fetchBestSellersProducts(int perPage, int categoryId) {
        Call<List<ProductItem>> call = mWoocommerceServiceProduct.listProductItems(
                NetworkParams.getMostExpensive(perPage, categoryId, NetworkParams.POPULAR));
        call.enqueue(getSortList(categoryId));
    }

    private Callback<List<ProductItem>> getSortList(int categoryId) {
        return new Callback<List<ProductItem>>() {

            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                mProductListLiveData.setValue(response.body());
                mCategoryItemId.setValue(categoryId);
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };
    }

    public void postCustomer(Customer customer) {
        Log.e("postCustomer", "post custoemr called");

        Call<Customer> call = mWoocommerceServiceCustomer.createCustomer(customer);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Log.e("postCustomer", "your customer post successfully");
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e(TAG, "post customer failed");
            }
        });
    }
}
