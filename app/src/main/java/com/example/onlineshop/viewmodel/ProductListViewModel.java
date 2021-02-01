package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.ProductRepository;

import java.util.List;

public class ProductListViewModel extends ProductViewModel {

    public static final String TAG = "ProductListViewModel";
    private ProductRepository mRepository;
    private final LiveData<List<ProductItem>> mProductListLiveData;
    private final LiveData<Integer> mPageCount;
    private final LiveData<Integer> mCategoryItemId;
    private final LiveData<Integer> mPerPage;

//    private final LiveData<ProductItem> mClickedItem;

    private MutableLiveData<Boolean> mOpenedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSortDialogStart = new MutableLiveData<>();
    private LiveData<List<ProductItem>> mSearchLiveData;

//    private MutableLiveData<ProductItem> mProductItemSelected;
//    private MutableLiveData<Integer> mClickedProductItem;

    public LiveData<List<ProductItem>> getProductListLiveData() {
        return mProductListLiveData;
    }

//    public MutableLiveData<ProductItem> getProductItemSelected() {
//        return mProductItemSelected;
//    }

    public MutableLiveData<Boolean> getSortDialogStart() {
        return mSortDialogStart;
    }

    public LiveData<Integer> getPageCount() {
        return mPageCount;
    }

    public LiveData<Boolean> getOpenedLiveData() {
        return mOpenedLiveData;
    }

    public LiveData<Integer> getCategoryItemId() {
        return mCategoryItemId;
    }

    public void fetchSearchItems(String query){
        mRepository.fetchSearchItems(query);
    }

    public LiveData<List<ProductItem>> getSearchLiveData() {
        return mSearchLiveData;
    }

    public LiveData<Integer> getPerPage() {
        return mPerPage;
    }

    //    public LiveData<ProductItem> getClickedItem() {
//        return mClickedItem;
//    }

//    public MutableLiveData<Integer> getClickedProductItem() {
//        mClickedProductItem = new MutableLiveData<>();
//        Log.e("productItemClicked", "this id clicked in pvm get LiveData");
//        return mClickedProductItem;
//    }

    public ProductListViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance();
        mProductListLiveData = mRepository.getProductListLiveData();
        mPageCount = mRepository.getPageCount();
        mCategoryItemId = mRepository.getCategoryItemId();
        mSearchLiveData = mRepository.getSearchItemsLiveData();
        mPerPage = mRepository.getPerPage();

//        mProductItemSelected = mRepository.getProductItemSelectedLiveData();
//        mClickedItem = mRepository.getProductItemLiveData();
    }

    public void openDrawer() {
        mOpenedLiveData.setValue(true);
        Log.d(TAG, "open drawer from view model called");
    }

    public void fetchProductItems(int page) {
        mRepository.fetchProductItemsAsync(page, mCategoryItemId.getValue());
    }

    public void sortClicked(){
        mSortDialogStart.setValue(true);
    }

//    public void sortClicked(Fragment fragment){
//        SortListFragment sortListFragment = SortListFragment.newInstance();
//
//        sortListFragment.setTargetFragment(fragment,
//                ProductListFragment.REQUEST_CODE_SORT_LIST);
//
//        sortListFragment.show(fragment.getParentFragmentManager(),
//                ProductListFragment.SORT_DIALOG_FRAGMENT_TAG);
//    }

//    public void searchClicked(){
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//    }
    //    public void onProductSelected(ProductItem item){
//        mProductItemSelected.setValue(item);
//    }

//    public void onProductItemClicked(int id){
//        Log.e("productItemClicked", "this id clicked in pvm" + id);
//        mClickedProductItem.setValue(id);
//    }
}
