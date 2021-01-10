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
import com.example.onlineshop.adapter.CartAdapter;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.FragmentCartBinding;
import com.example.onlineshop.viewmodel.CartViewModel;

import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding mBinding;
    private CartViewModel mViewModel;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        setLiveDataObservers();
    }

    private void setLiveDataObservers(){
        mViewModel.getCartProductItem().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setupAdapter();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_cart,
                container,
                false);
        initViews();
        mBinding.setCartViewModel(mViewModel);

        return mBinding.getRoot();
    }

    private void initViews(){
        mBinding.rvCartProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupAdapter(){
        CartAdapter adapter = new CartAdapter(mViewModel);
        mBinding.rvCartProducts.setAdapter(adapter);
    }
}