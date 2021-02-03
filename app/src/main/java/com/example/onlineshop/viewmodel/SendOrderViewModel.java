package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.data.model.CouponLines;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.LineItem;
import com.example.onlineshop.data.model.Order;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.CustomerRepository;
import com.example.onlineshop.data.repository.ProductRepository;
import com.example.onlineshop.databinding.ItemAddressListBinding;
import com.example.onlineshop.utilities.QueryPreferences;

import java.util.ArrayList;
import java.util.List;

public class SendOrderViewModel extends AndroidViewModel {

    private CustomerRepository mRepository;
    private ProductRepository mProductRepository;
    private LiveData<Customer> mCustomerLiveData;
    private LiveData<Order> mOrderLiveData;
    private LiveData<CouponLines> mCouponLinesLiveData;
    private MutableLiveData<Integer> mTotalPrice  = new MutableLiveData<>();
    private MutableLiveData<List<ProductItem>> mCartProductItem;

    private String mCoupon;
    private int total = 0;
    private CouponLines mCouponLines;

    public int getTotal() {
        return total;
    }

    public LiveData<Order> getOrderLiveData() {
        return mOrderLiveData;
    }

    public LiveData<CouponLines> getCouponLinesLiveData() {
        return mCouponLinesLiveData;
    }

    public MutableLiveData<List<ProductItem>> getCartProductItem() {
        return mCartProductItem;
    }

    public MutableLiveData<Integer> getTotalPrice() {
        return mTotalPrice;
    }

    public SendOrderViewModel(@NonNull Application application) {
        super(application);
        mRepository = CustomerRepository.getInstance();
        mProductRepository = ProductRepository.getInstance();
        mCustomerLiveData = mRepository.getCustomerLiveData();
        mOrderLiveData = mRepository.getOrderLiveData();
        mCouponLinesLiveData = mRepository.getCouponLinesLiveData();
        mCartProductItem = mRepository.getProductItemsInCart();
    }

    public LiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public String getCurrentUser(){
        return QueryPreferences.getUserNameQuery(getApplication());
    }
    public String setTotalPrice() {
        List<ProductItem> items = QueryPreferences.getCartProducts(getApplication());
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                total += Integer.parseInt(items.get(i).getProductPrice());
            }
            return total + " تومان ";
        } else
            return "";
    }

    public void sendOrder() {
        List<ProductItem> items = QueryPreferences.getCartProducts(getApplication());
        List<LineItem> lineItems = new ArrayList<>();
        if(items != null) {
            for (int i = 0; i < items.size(); i++) {
                LineItem item = new LineItem(items.get(i).getId(), 1);
                lineItems.add(item);
            }
            Order order = new Order(QueryPreferences.getIdQuery(getApplication()), lineItems);
            mRepository.sendOrder(order);
        }
//        if(getOrderLiveData().getValue() != null)
//            QueryPreferences.removeAllCartProducts(getApplication());
    }

    public void onTextChangedCoupon(CharSequence charSequence, int i, int i1, int i2) {
        mCoupon = charSequence.toString();
    }

    public void insertCode(){
        mRepository.getCoupon(mCoupon);
//        int finalTotal = total - Integer.parseInt(getCouponLinesLiveData().getValue().getAmount());
//        mTotalPrice.setValue(total);
    }

    public void deleteCartItems(boolean flag){
        mProductRepository.deleteCartItems(flag);
        QueryPreferences.removeAllCartProducts(getApplication());
    }
}
