package com.example.onlineshop.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSendOrderBinding;
import com.example.onlineshop.viewmodel.SendOrderViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class SendOrderFragment extends BottomSheetDialogFragment {

    private FragmentSendOrderBinding mBinding;
    private SendOrderViewModel mOrderViewModel;

    public SendOrderFragment() {
        // Required empty public constructor
    }

    public static SendOrderFragment newInstance() {
        SendOrderFragment fragment = new SendOrderFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderViewModel = new ViewModelProvider(this).get(SendOrderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_send_order,
                container,
                false);
        mBinding.setSendOrderViewModel(mOrderViewModel);

        return mBinding.getRoot();
    }
}