package com.example.onlineshop.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.FragmentProductListBinding;
import com.example.onlineshop.view.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private FragmentProductListBinding mBinding;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_product_list,
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