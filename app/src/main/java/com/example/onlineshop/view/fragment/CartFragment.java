package com.example.onlineshop.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.CartAdapter;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.FragmentCartBinding;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.CartViewModel;

import java.util.List;

public class CartFragment extends VisibleFragment {

    private FragmentCartBinding mBinding;
    private CartViewModel mViewModel;
    private CartAdapter mAdapter;

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
        mViewModel.fetchCartItems();

        setLiveDataObservers();
    }

    private void setLiveDataObservers() {
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

        mViewModel.getStartAccountDialog().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setMessage(R.string.please_login);
                dialogBuilder.setCancelable(true);
                dialogBuilder.setPositiveButton(R.string.navigate_to_account, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(mBinding.getRoot()).navigate(R.id.accountFragment);
                        dialog.dismiss();
                    }
                });
                dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.show();
            }
        });

        LiveData<Boolean> sendDialogLiveData = mViewModel.getSendDialogLiveData();
        sendDialogLiveData.observe(getViewLifecycleOwner(), new SingleEventObserver<Boolean>(this, sendDialogLiveData) {
            @Override
            public void onChanged(Boolean aBoolean) {
                super.onChanged(aBoolean);
                if (aBoolean) {
                    Navigation.findNavController(mBinding.getRoot()).navigate(R.id.sendOrderFragment);
                }
            }
        });
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.rvCartProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupAdapter() {
        mAdapter = new CartAdapter(mViewModel);
        mBinding.rvCartProducts.setAdapter(mAdapter);
    }
}