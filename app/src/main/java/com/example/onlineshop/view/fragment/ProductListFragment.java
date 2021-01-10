package com.example.onlineshop.view.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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

//        NavController navController = NavHostFragment.findNavController(this);
////        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.nav_host_fragment);
////        ProductListViewModel viewModel = new ViewModelProvider(backStackEntry).get(ProductListViewModel.class);
//        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.nav_host_fragment);
//
//        ViewModelProvider viewModelProvider = new ViewModelProvider(
//                backStackEntry.getViewModelStore(),
//                new SavedStateViewModelFactory(
//                        requireActivity().getApplication(), requireParentFragment()));

//        mProductListViewModel =new ViewModelProvider(backStackEntry).get(ProductListViewModel.class);
        mProductListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        setLiveDataObservers();

        NavController navController = NavHostFragment.findNavController(this);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navController.popBackStack(R.id.action_categoryListFragment_to_productListFragment, true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

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

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        mBinding.ivSearch.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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
//        layoutManager.setReverseLayout(true);
        mBinding.rvProducts.setLayoutManager(layoutManager);
    }

    private void scrollListener() {
        mBinding.rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == 1) {
                    if (page <= mProductListViewModel.getPageCount().getValue() &&
                            mProductListViewModel.getProductListLiveData().getValue().size() == 10)

                        mProductListViewModel.fetchProductItems(++page);
                }
            }
        });

    }

    private void setupAdapter(List<ProductItem> items) {
        ProductsListAdapter adapter = new ProductsListAdapter(this, mProductListViewModel,
                mProductListViewModel.getProductListLiveData().getValue(), 0);
        mBinding.rvProducts.setAdapter(adapter);
    }
}