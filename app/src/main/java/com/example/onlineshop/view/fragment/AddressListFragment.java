package com.example.onlineshop.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.AddressAdapter;
import com.example.onlineshop.adapter.CartAdapter;
import com.example.onlineshop.databinding.FragmentAddressListBinding;
import com.example.onlineshop.viewmodel.AccountViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Set;

public class AddressListFragment extends BottomSheetDialogFragment {

    private FragmentAddressListBinding mBinding;
    private AccountViewModel mViewModel;

    public AddressListFragment() {
        // Required empty public constructor
    }

    public static AddressListFragment newInstance() {
        AddressListFragment fragment = new AddressListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        mViewModel.setAddressItems();

        setLiveDataObservers();
    }

    private void setLiveDataObservers(){
        mViewModel.getAddressListLiveData().observe(this, new Observer<Set<String>>() {
            @Override
            public void onChanged(Set<String> strings) {
                setupAdapter(strings);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_address_list,
                container,
                false);
        initViews();
        dismissDialog();
        return mBinding.getRoot();
    }

    private void dismissDialog() {
        mViewModel.getClickedAddress().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismiss();
            }
        });
    }

    private void initViews() {
        mBinding.rvAddresses.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupAdapter(Set<String> addresses) {
        AddressAdapter adapter = new AddressAdapter(addresses, mViewModel);
        mBinding.rvAddresses.setAdapter(adapter);
    }
}