package com.example.onlineshop.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.CouponLines;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.Order;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.model.Review;
import com.example.onlineshop.data.remote.NetworkParams;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstance;
import com.example.onlineshop.data.remote.retrofit.WoocommerceService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository {

    public static final String TAG = "CustomerRepository";
    private static CustomerRepository sInstance;
    private WoocommerceService mWoocommerceServiceCustomer;
    private WoocommerceService mWoocommerceServiceReview;
    private WoocommerceService mWoocommerceServiceCoupon;

    private MutableLiveData<Customer> mCustomerLiveData = new MutableLiveData<>();
    private MutableLiveData<Customer> mSearchEmailLiveData = new MutableLiveData<>();
    private MutableLiveData<Order> mOrderLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mSendReview = new MutableLiveData<>();
    private MutableLiveData<List<Review>> mReviewsLiveData = new MutableLiveData<>();
    private MutableLiveData<CouponLines> mCouponLinesLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mCurrentAddressLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> mProductItemsInCart = new MutableLiveData<>();

    public void setCustomerSettingLiveData() {
        mSendReview = new MutableLiveData<>();
        mOrderLiveData = new MutableLiveData<>();
    }

    public void setCustomerSettingCartLiveData() {
        setCustomerSettingLiveData();
        mCustomerLiveData.setValue(null);
    }

    public MutableLiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public MutableLiveData<Customer> getSearchEmailLiveData() {
        return mSearchEmailLiveData;
    }

    public MutableLiveData<Order> getOrderLiveData() {
        return mOrderLiveData;
    }

    public MutableLiveData<List<Review>> getReviewsLiveData() {
        return mReviewsLiveData;
    }

    public MutableLiveData<Boolean> getSendReview() {
        return mSendReview;
    }

    public MutableLiveData<CouponLines> getCouponLinesLiveData() {
        return mCouponLinesLiveData;
    }

    public MutableLiveData<String> getCurrentAddressLiveData() {
        return mCurrentAddressLiveData;
    }

    public MutableLiveData<List<ProductItem>> getProductItemsInCart() {
        return mProductItemsInCart;
    }

    public static CustomerRepository getInstance() {
        if (sInstance == null)
            sInstance = new CustomerRepository();
        return sInstance;
    }

    private CustomerRepository() {
        mWoocommerceServiceCustomer = RetrofitInstance.getCustomerInstance().create(WoocommerceService.class);
        mWoocommerceServiceReview = RetrofitInstance.getReviewInstance().create(WoocommerceService.class);
        mWoocommerceServiceCoupon = RetrofitInstance.getCouponInstance().create(WoocommerceService.class);
    }

    public void createCustomer(Customer customer) {

        Call<Customer> call = mWoocommerceServiceCustomer.createCustomer(
                "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/customers",
                customer,
                NetworkParams.CONSUMER_KEY,
                NetworkParams.CONSUMER_SECRET
        );
        call.enqueue(new Callback<Customer>() {

            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                Log.e(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    mCustomerLiveData.setValue(response.body());

                    Log.e(TAG, "onResponse: " + response.body().getEmail());
                    Log.e(TAG, "customer Id: " + response.body().getId());
                } else {
                    mCustomerLiveData.setValue(null);
                    Log.e(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("postCustomer", t.getMessage());
            }
        });
    }

    public void searchCustomer(String email) {
        Call<List<Customer>> call =
                mWoocommerceServiceCustomer.getCustomer(NetworkParams.getCustomer(email));
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {

                        mCustomerLiveData.setValue(response.body().get(0));
                    Log.e(TAG, "searchEmail:" + response.body().get(0).getEmail());
                    Log.e(TAG, "searchId:" + response.body().get(0).getId());

                    mSearchEmailLiveData.setValue(response.body().get(0));
                } else
                    mSearchEmailLiveData.setValue(null);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    public void sendOrder(Order order) {
        Call<Order> call = mWoocommerceServiceCustomer.sendOrder(
                "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/orders",
                order,
                NetworkParams.CONSUMER_KEY,
                NetworkParams.CONSUMER_SECRET
        );
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    mOrderLiveData.setValue(response.body());
                    Log.e(TAG, "post order: " + response.code());
                    Log.e(TAG, "post order: " + response.body().getBilling().getEmail());
                } else
                    mOrderLiveData.setValue(null);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

    public void fetchProductReviews(int productId) {
        Call<List<Review>> call =
                mWoocommerceServiceReview.getReviews(NetworkParams.getReviews(productId));
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                mReviewsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });
    }

    public void sendReview(Review review) {
        Call<Review> call = mWoocommerceServiceReview.sendReview(
                "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/products/reviews",
                review,
                NetworkParams.CONSUMER_KEY,
                NetworkParams.CONSUMER_SECRET
        );
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful())
                    mSendReview.setValue(true);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }

    public void getCoupon(String code) {
        Call<List<CouponLines>> call =
                mWoocommerceServiceCoupon.getCoupon(NetworkParams.getCouponOptions(code));
        call.enqueue(new Callback<List<CouponLines>>() {
            @Override
            public void onResponse(Call<List<CouponLines>> call, Response<List<CouponLines>> response) {
                mCouponLinesLiveData.setValue(response.body().get(0));
                Log.e("coupon", "coupon code: " + response.body().get(0).getAmount());
            }

            @Override
            public void onFailure(Call<List<CouponLines>> call, Throwable t) {

            }
        });
    }

    public void getCurrentAddress(String address) {
        mCurrentAddressLiveData.setValue(address);
    }

}
