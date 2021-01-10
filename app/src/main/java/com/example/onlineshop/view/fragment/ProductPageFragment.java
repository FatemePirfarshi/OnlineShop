package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductsListAdapter;
import com.example.onlineshop.adapter.SliderAdapter;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.FragmentProductPageBinding;
import com.example.onlineshop.viewmodel.ProductListViewModel;
import com.example.onlineshop.viewmodel.ProductPageViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.List;

public class ProductPageFragment extends Fragment {

    public static final String TAG = "ProductPageFragment";
    private FragmentProductPageBinding mBinding;
    private ProductPageViewModel mProductPageViewModel;
    private ProductListViewModel mProductListViewModel;

    public ProductPageFragment() {
        // Required empty public constructor
    }

    public static ProductPageFragment newInstance() {
        ProductPageFragment fragment = new ProductPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductPageViewModel = new ViewModelProvider(this).get(ProductPageViewModel.class);
        mProductListViewModel = new  ViewModelProvider(this).get(ProductListViewModel.class);

        if (getArguments() != null) {
            int id = getArguments().getInt("productId");
            Log.e("productItemClicked", "this id clicked in ppf " + id);
//            mProductPageViewModel.fetchProductItemWithId(id);
        }

        mProductPageViewModel.getProductItemLiveData().observe(this, new Observer<ProductItem>() {
            @Override
            public void onChanged(ProductItem productItem) {
                mBinding.setProductPageViewModel(mProductPageViewModel);
//                mProductPageViewModel.fetchRelatedItems(productItem.getRelatedIds());

                ArrayList<String> itemImages = (ArrayList<String>) productItem.getImages();
                setupSliderAdapter(itemImages);


                mProductPageViewModel.getRelatedItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<ProductItem>>() {
                    @Override
                    public void onChanged(List<ProductItem> productItems) {
//                Log.e(TAG, productItems.get(0).getProductName());
                        setupAdapter(productItems);
                    }
                });
            }
        });

//        mProductPageViewModel.getRelatedItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
//            @Override
//            public void onChanged(List<ProductItem> productItems) {
////                Log.e(TAG, productItems.get(0).getProductName());
//                setupAdapter(productItems);
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_page,
                container,
                false);
        initViews();

        return mBinding.getRoot();
    }


    private void initViews(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                true);
        mBinding.rvProducts.setLayoutManager(layoutManager);
    }

    private void setupAdapter(List<ProductItem> items) {
        ProductsListAdapter adapter = new ProductsListAdapter(this, mProductListViewModel,
                mProductPageViewModel.getRelatedItemsLiveData().getValue(), 0);
        mBinding.rvProducts.setAdapter(adapter);
    }

    private void setupSliderAdapter(ArrayList<String> itemImages) {
        mBinding.imageSlider.setSliderAdapter(new SliderAdapter(getContext(), itemImages));
        mBinding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mBinding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mBinding.imageSlider.startAutoCycle();
    }
}
