package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductsListAdapter;
import com.example.onlineshop.adapter.ReviewAdapter;
import com.example.onlineshop.adapter.SliderAdapter;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.model.Review;
import com.example.onlineshop.databinding.FragmentProductPageBinding;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.ProductListViewModel;
import com.example.onlineshop.viewmodel.ProductPageViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.List;

public class ProductPageFragment extends VisibleFragment {

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
        }

        mProductPageViewModel.getProductItemLiveData().observe(this, new Observer<ProductItem>() {
            @Override
            public void onChanged(ProductItem productItem) {
                mBinding.setProductPageViewModel(mProductPageViewModel);

                ArrayList<String> itemImages = (ArrayList<String>) productItem.getImages();
                setupSliderAdapter(itemImages);

            }
        });

        mProductPageViewModel.getRelatedItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
//                Log.e(TAG, productItems.get(0).getProductName());
                setupAdapter(productItems);
            }
        });

        mProductPageViewModel.getReviewsLiveData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
//                Log.e("review",reviews.get(0).getReview());
                setupReviewAdapter(reviews);
            }
        });

        LiveData<Boolean> sendReviewLiveData = mProductPageViewModel.getSendReviewLiveData();
        sendReviewLiveData.observe(getViewLifecycleOwner(), new SingleEventObserver<Boolean>(this, sendReviewLiveData) {
            @Override
            public void onChanged(Boolean aBoolean) {
                super.onChanged(aBoolean);
                if (aBoolean)
                    Toast.makeText(
                            getActivity(),
                            R.string.send_review_successfuly,
                            Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(
                            getActivity(),
                            R.string.please_try_again,
                            Toast.LENGTH_LONG).show();
            }
        });
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
        navListener();

        return mBinding.getRoot();
    }

    private void navListener() {
        mProductListViewModel.getClickedProductItem().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer id) {
                Log.e("productItemClicked", "this id clicked in pvm change LiveData");
                Bundle bundle = new Bundle();
                bundle.putInt("productId", id);
                Navigation.findNavController(mBinding.getRoot()).navigate(R.id.productPageFragment, bundle);
            }
        });
    }


    private void initViews(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                true);
        mBinding.rvProducts.setLayoutManager(layoutManager);

        mBinding.rvReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupAdapter(List<ProductItem> items) {
//        Log.e("RelatedItems", mProductPageViewModel.getRelatedItemsLiveData().getValue().get(0).getProductName());
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

    private void setupReviewAdapter(List<Review> reviews){
        ReviewAdapter adapter = new ReviewAdapter(mProductPageViewModel);
        mBinding.rvReviews.setAdapter(adapter);
    }
}
