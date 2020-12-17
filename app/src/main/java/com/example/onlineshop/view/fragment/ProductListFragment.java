package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductAdapter;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.FragmentProductListBinding;
import com.example.onlineshop.viewmodel.ProductListViewModel;

import java.util.List;

public class ProductListFragment extends Fragment {

    public static final String ARGS_STATE_OF_LIST = "stateOfList";
    public static final String ARGS_NAME_OF_LIST = "nameOfList";
    private FragmentProductListBinding mBinding;
    private int state;
    private String title;
    private ProductListViewModel mProductListViewModel;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance(int state, String name) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_STATE_OF_LIST, state);
        args.putString(ARGS_NAME_OF_LIST, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        state = getArguments().getInt(ARGS_STATE_OF_LIST);
        title = getArguments().getString(ARGS_NAME_OF_LIST);

        mProductListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);

        mProductListViewModel.fetchItems();
        setLiveDataObservers();
    }

    private void setLiveDataObservers() {
        mProductListViewModel.getListLiveData().observe(this, new Observer<List<ProductItem>>() {
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
                R.layout.fragment_product_list,
                container,
                false);

        initViews();

        return mBinding.getRoot();
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                true);
        layoutManager.setReverseLayout(true);
        mBinding.recyclerViewProducts.setLayoutManager(layoutManager);
//        mBinding.listTitle.setText(title);
    }

    private void setupAdapter(List<ProductItem> items) {
        ProductAdapter adapter = new ProductAdapter(mProductListViewModel);
        mBinding.recyclerViewProducts.setAdapter(adapter);
    }
}