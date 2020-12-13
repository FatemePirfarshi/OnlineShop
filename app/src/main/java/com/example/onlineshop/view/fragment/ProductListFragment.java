package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductAdapter;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.FragmentProductListBinding;
import com.example.onlineshop.viewmodel.ProductListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    public static final String ARGS_STATE_OF_LIST = "stateOfList";
    private FragmentProductListBinding mBinding;
    private int state;
    private ProductListViewModel mProductListViewModel;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance(int state) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_STATE_OF_LIST, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        state = getArguments().getInt(ARGS_STATE_OF_LIST);
        mProductListViewModel= new ViewModelProvider(this).get(ProductListViewModel.class);
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
        setupAdapter();
        return mBinding.getRoot();
    }

    private void initViews(){
        mBinding.recyclerViewProducts.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false));
    }

    private void setupAdapter(){
        //todo
        List<ProductItem> items = new ArrayList<>();
        items.add(new ProductItem());
        items.add(new ProductItem());
        items.add(new ProductItem());
        ProductAdapter adapter = new ProductAdapter(getActivity(), items);
        mBinding.recyclerViewProducts.setAdapter(adapter);
    }
}