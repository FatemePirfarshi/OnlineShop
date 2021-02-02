package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.data.model.CouponLines;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.LineItem;
import com.example.onlineshop.data.model.Order;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.CustomerRepository;
import com.example.onlineshop.utilities.QueryPreferences;

import java.util.ArrayList;
import java.util.List;

public class SendOrderViewModel extends AndroidViewModel {

    private CustomerRepository mRepository;
    private LiveData<Customer> mCustomerLiveData;
    private String mCoupon;
    private CouponLines mCouponLines;

    public SendOrderViewModel(@NonNull Application application) {
        super(application);
        mRepository = CustomerRepository.getInstance();
        mCustomerLiveData = mRepository.getCustomerLiveData();
    }

    public LiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public String setTotalPrice() {
        int total = 0;
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
        for (int i = 0; i < items.size(); i++) {
            LineItem item = new LineItem(items.get(i).getId(), 1);
            lineItems.add(item);
        }
        Order order = new Order(QueryPreferences.getIdQuery(getApplication()), lineItems);
        mRepository.sendOrder(order);
    }

    public void onTextChangedCoupon(CharSequence charSequence, int i, int i1, int i2) {
        mCoupon = charSequence.toString();
    }

    public void insertCode(){
//        mCouponLines = new CouponLines(mCoupon,);
    }
}
