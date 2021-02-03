package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.model.Review;
import com.example.onlineshop.data.repository.CustomerRepository;
import com.example.onlineshop.data.repository.ProductRepository;
import com.example.onlineshop.utilities.QueryPreferences;

import java.util.List;

public class ProductPageViewModel extends ProductViewModel {

    private final LiveData<ProductItem> mProductItemLiveData;
    private final LiveData<List<ProductItem>> mRelatedItemsLiveData;
    private final LiveData<List<Review>> mReviewsLiveData;
    private ProductRepository mRepository;
    private CustomerRepository mCustomerRepository;
    private LiveData<Boolean> mSendReviewLiveData;

    private String mRating;
    private String mReview;

    public ProductPageViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
        mProductItemLiveData = mRepository.getProductItemLiveData();
        mRelatedItemsLiveData = mRepository.getRelatedItemsLiveData();
        mCustomerRepository = CustomerRepository.getInstance();
        mReviewsLiveData = mCustomerRepository.getReviewsLiveData();
        mSendReviewLiveData = mCustomerRepository.getSendReview();
//        Log.e("RelatedItemsViewModel", mRepository.getRelatedItemsLiveData().getValue().get(0).getProductName());
    }

    public LiveData<ProductItem> getProductItemLiveData() {
        return mProductItemLiveData;
    }

    public LiveData<List<ProductItem>> getRelatedItemsLiveData() {
        return mRelatedItemsLiveData;
    }

    public LiveData<List<Review>> getReviewsLiveData() {
        return mReviewsLiveData;
    }

    public LiveData<Boolean> getSendReviewLiveData() {
        return mSendReviewLiveData;
    }

    public void fetchRelatedItems(List<Integer> relatedProductsId){
        mRepository.fetchRelatedItems(relatedProductsId);
    }

    public void addToCartClicked(){
        ProductItem item = mProductItemLiveData.getValue();
        item.setCountInCart(1);
        QueryPreferences.addCartProduct(getApplication(), item);
        mRepository.setCartItemLiveData(QueryPreferences.getCartProducts(getApplication()));
    }

    public void onTextChangedReview(CharSequence charSequence, int i, int i1, int i2) {
        mReview = charSequence.toString();
    }

    public void onTextChangedRating(CharSequence charSequence, int i, int i1, int i2) {
        mRating = charSequence.toString();
    }

    public void sendReview(){
        Review review = new Review(
                QueryPreferences.getUserNameQuery(getApplication()),
                QueryPreferences.getEmailQuery(getApplication()),
                Integer.parseInt(mRating),
                mReview,
                getProductItemLiveData().getValue().getId());
        mCustomerRepository.sendReview(review);
    }

    public void setCustomerSettingLiveData(){
        mCustomerRepository.setCustomerSettingLiveData();
    }
}
