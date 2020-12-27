package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
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
import com.example.onlineshop.databinding.FragmentProductsListBinding;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.ProductListViewModel;

import java.util.List;

public class ProductListFragment extends Fragment {

    public static final String ARGS_STATE_OF_LIST = "stateOfList";
    public static final String ARGS_NAME_OF_LIST = "nameOfList";
    private FragmentProductsListBinding mBinding;
    //    private int state;
//    private String title;
    private ProductListViewModel mProductListViewModel;
    private int page = 1;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
//        args.putInt(ARGS_STATE_OF_LIST, state);
//        args.putString(ARGS_NAME_OF_LIST, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        state = getArguments().getInt(ARGS_STATE_OF_LIST);
//        title = getArguments().getString(ARGS_NAME_OF_LIST);

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
        return mBinding.getRoot();
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
//        mBinding.listTitle.setText(title);
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
                mProductListViewModel.getProductListLiveData().getValue());
        mBinding.rvProducts.setAdapter(adapter);
    }
}