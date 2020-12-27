package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductsListAdapter;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.FragmentHomeBinding;
import com.example.onlineshop.viewmodel.ProductListViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

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

        mProductListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        mProductListViewModel.fetchTotalProducts();
        setLiveDataObservers();
    }

    private void setLiveDataObservers() {
        mProductListViewModel.getPerPage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer perPage) {
                mProductListViewModel.fetchPopularItems(perPage);
                mProductListViewModel.fetchRecentItems(perPage);
                mProductListViewModel.fetchTopItems(perPage);
            }
        });
        mProductListViewModel.getPopularItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setupAdapter(mProductListViewModel.getPopularItemsLiveData(), mBinding.rvPopular);
            }
        });

        mProductListViewModel.getRecentItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setupAdapter(mProductListViewModel.getRecentItemsLiveData(), mBinding.rvNewest);
            }
        });

        mProductListViewModel.getTopItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setupAdapter(mProductListViewModel.getTopItemsLiveData(), mBinding.rvTop);
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
        return mBinding.getRoot();
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

    private void setupAdapter(LiveData<List<ProductItem>> productItemsLiveData, RecyclerView rv) {
        ProductsListAdapter popularAdapter = new ProductsListAdapter(mProductListViewModel,
                productItemsLiveData.getValue());
        rv.setAdapter(popularAdapter);
    }
}