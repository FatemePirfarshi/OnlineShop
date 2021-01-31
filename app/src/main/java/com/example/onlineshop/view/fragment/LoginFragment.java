package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentLoginBinding;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.AccountViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LoginFragment extends BottomSheetDialogFragment {

    private FragmentLoginBinding mBinding;
    private AccountViewModel mViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false);
        mBinding.setAccountViewModel(mViewModel);

        LiveData<Boolean> loginAccountLiveData = mViewModel.getLoginAccount();
        loginAccountLiveData.observe(getViewLifecycleOwner(), new SingleEventObserver<Boolean>(this, loginAccountLiveData) {
            @Override
            public void onChanged(Boolean aBoolean) {
                super.onChanged(aBoolean);
                if (aBoolean) {
                    dismiss();
                }
            }
        });

        return mBinding.getRoot();
    }
}