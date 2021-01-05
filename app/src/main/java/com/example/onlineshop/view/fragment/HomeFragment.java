package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductsListAdapter;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.FragmentHomeBinding;
import com.example.onlineshop.viewmodel.HomeViewModel;
import com.example.onlineshop.viewmodel.ProductListViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

//    private ProductListViewModel mProductListViewModel;
    private HomeViewModel mHomeViewModel;
    private ProductListViewModel mProductListViewModel;
    private FragmentHomeBinding mBinding;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mProductListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mProductListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);

        mHomeViewModel.fetchTotalProducts();
        setLiveDataObservers();
    }

    private void setLiveDataObservers() {
        mHomeViewModel.getPerPage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer perPage) {
                mHomeViewModel.fetchPopularItems(perPage);
                mHomeViewModel.fetchRecentItems(perPage);
                mHomeViewModel.fetchTopItems(perPage);
            }
        });
        mHomeViewModel.getPopularItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setupAdapter(mHomeViewModel.getPopularItemsLiveData(), mBinding.rvPopular, 1);
            }
        });

        mHomeViewModel.getRecentItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setupAdapter(mHomeViewModel.getRecentItemsLiveData(), mBinding.rvNewest, 2);
            }
        });

        mHomeViewModel.getTopItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setupAdapter(mHomeViewModel.getTopItemsLiveData(), mBinding.rvTop, 3);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home,
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
//                Log.e("productItemClicked", "this id clicked in pvm change LiveData");
                Bundle bundle = new Bundle();
                bundle.putInt("productId", id);
                Navigation.findNavController(mBinding.getRoot()).navigate(R.id.productPageFragment, bundle);
            }
        });
    }

    private void initViews() {
        LinearLayoutManager layoutManagerPopular = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                true);
        layoutManagerPopular.setReverseLayout(true);
        mBinding.rvPopular.setLayoutManager(layoutManagerPopular);

        LinearLayoutManager layoutManagerRecent = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                true);
        layoutManagerRecent.setReverseLayout(true);
        mBinding.rvNewest.setLayoutManager(layoutManagerRecent);

        LinearLayoutManager layoutManagerTop = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                true);
        layoutManagerRecent.setReverseLayout(true);
        mBinding.rvTop.setLayoutManager(layoutManagerTop);
    }

    private void setupAdapter(LiveData<List<ProductItem>> productItemsLiveData,
                              RecyclerView rv,
                              int listPosition) {
        ProductsListAdapter popularAdapter = new ProductsListAdapter(this, mProductListViewModel,
                productItemsLiveData.getValue(), listPosition);
        rv.setAdapter(popularAdapter);
    }
}