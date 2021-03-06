package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.CustomerRepository;
import com.example.onlineshop.data.repository.ProductRepository;
import com.example.onlineshop.utilities.QueryPreferences;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private CustomerRepository mCustomerRepository;
    private MutableLiveData<List<ProductItem>> mCartProductItem;
    private MutableLiveData<Integer> mCountProductLiveData = new MutableLiveData<>();
    private MutableLiveData<ProductItem> mCurrentItem = new MutableLiveData<>();
    private MutableLiveData<Boolean> mStartAccountDialog = new MutableLiveData<>();
    private MutableLiveData<Boolean> mSendDialogLiveData = new MutableLiveData<>();

    public CartViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
        mCustomerRepository = CustomerRepository.getInstance();
        mCartProductItem = mRepository.getCartItemLiveData();
    }

    public MutableLiveData<List<ProductItem>> getCartProductItem() {
        return mCartProductItem;
    }

    public MutableLiveData<Integer> getCountProductLiveData() {
        return mCountProductLiveData;
    }

    public MutableLiveData<ProductItem> getCurrentItem() {
        return mCurrentItem;
    }

    public MutableLiveData<Boolean> getStartAccountDialog() {
        return mStartAccountDialog;
    }

    public MutableLiveData<Boolean> getSendDialogLiveData() {
        return mSendDialogLiveData;
    }

    public void deleteFromCart(ProductItem item) {
        QueryPreferences.removeCartProduct(getApplication(), item);
        Log.e("Delete", mCartProductItem.getValue().get(0) + "deleted");
        for (int i = 0; i < QueryPreferences.getCartProducts(getApplication()).size(); i++) {
            Log.e("Dalate","cartProducts "+ QueryPreferences.getCartProducts(getApplication()).get(i).getProductName());
        }
    }

//    public void deleteFromCartWithPosition(int position){
//        QueryPreferences.removeCartProductWithPosition(getApplication(), position);
//    }

    public void addAgain(ProductItem item) {
        int count = item.getCountInCart();
        item.setCountInCart(count++);
        mCountProductLiveData.setValue(count);
        mCurrentItem.setValue(item);
//        QueryPreferences.addCartProduct(getApplication(), item);
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

    public String setItemCount(int position) {
        return String.valueOf(mCartProductItem.getValue().get(position).getCountInCart());
    }

    public void fetchCartItems() {
        List<ProductItem> cartItems = QueryPreferences.getCartProducts(getApplication());
        if (cartItems != null)
            mCartProductItem.setValue(cartItems);
    }
//    public void getCartItems(){
//        List<ProductItem> cartItems = QueryPreferences.getCartProducts(getApplication());
//    }

    public void requestOrder() {
        if (QueryPreferences.getEmailQuery(getApplication()) == null)
            mStartAccountDialog.setValue(true);
        else
            mSendDialogLiveData.setValue(true);
    }

    public void setCustomerSettingLiveData() {
        mCustomerRepository.setCustomerSettingCartLiveData();
    }
}
