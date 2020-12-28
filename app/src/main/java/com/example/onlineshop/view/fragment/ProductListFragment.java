package com.example.onlineshop.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
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
import com.example.onlineshop.databinding.FragmentProductsListBinding;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.ProductListViewModel;

import java.util.List;

public class ProductListFragment extends Fragment {

    private FragmentProductsListBinding mBinding;
    private ProductListViewModel mProductListViewModel;
    private int page = 1;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProductListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        setLiveDataObservers();
    }

    private void setLiveDataObservers() {
        mProductListViewModel.getProductListLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setupAdapter(productItems);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_products_list,
                container,
                false);

        mBinding.setProductListViewModel(mProductListViewModel);

        initViews();
        scrollListener();
        openDrawer();
        navListener();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String categoryName = getArguments().getString("categoryName");
            mBinding.listTitle.setText(categoryName);
        }
    }

    private void navListener() {
        mProductListViewModel.getProductPageUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                if (uri != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("productPageUri", uri);
                    Navigation.findNavController(mBinding.getRoot())
                            .navigate(R.id.productPageFragment, bundle);
                }
            }
        });
    }

    private void openDrawer() {
        LiveData<Boolean> openedLiveData = mProductListViewModel.getOpenedLiveData();
        openedLiveData.observe(getActivity(),
                new SingleEventObserver<Boolean>(this, openedLiveData) {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            if (!mBinding.drawerLayoutCategories.isDrawerOpen(GravityCompat.START))
                                mBinding.drawerLayoutCategories.openDrawer(GravityCompat.START);
                        }
                    }
                });
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                true);
        layoutManager.setReverseLayout(true);
        mBinding.rvProducts.setLayoutManager(layoutManager);
    }

    private void scrollListener() {
        mBinding.rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (page <= mProductListViewModel.getPageCount().getValue() &&
                        mProductListViewModel.getProductListLiveData().getValue().size() == 10)

                    mProductListViewModel.fetchProductItems(++page);
            }
        });
    }

    private void setupAdapter(List<ProductItem> items) {
        ProductsListAdapter adapter = new ProductsListAdapter(mProductListViewModel,
                mProductListViewModel.getProductListLiveData().getValue(), 0);
        mBinding.rvProducts.setAdapter(adapter);
    }

}