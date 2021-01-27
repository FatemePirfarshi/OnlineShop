package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAccountBinding;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.AccountViewModel;

public class AccountFragment extends VisibleFragment {

    private AccountViewModel mAccountViewModel;
//    private ChooseAlarmViewModel mChooseAlarmViewModel;
    private FragmentAccountBinding mBinding;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
//        mChooseAlarmViewModel = new ViewModelProvider(this).get(ChooseAlarmViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_account,
                container,
                false);

        mBinding.setAccountViewModel(mAccountViewModel);
//        setLiveDataObservers();
        navListener();

        return mBinding.getRoot();
    }

//    private void setLiveDataObservers() {
//        LiveData<Boolean> toggleLiveData = mAccountViewModel.alarmClicked();
//        toggleLiveData.observe(getViewLifecycleOwner(), new SingleEventObserver<Boolean>(this, toggleLiveData) {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                super.onChanged(aBoolean);
//                if (aBoolean) {
//                    if (mAccountViewModel.isTaskScheduled())
//                        mBinding.ivAlarm.setImageResource(R.drawable.ic_active_alarm);
//                    else
//                        mBinding.ivAlarm.setImageResource(R.drawable.ic_deactive_alarm);
//                }
//            }
//        });
//    }

    private void navListener() {
        LiveData<Boolean> alarmDialogLiveData = mAccountViewModel.getAlarmDialogStart();
        alarmDialogLiveData.observe(getViewLifecycleOwner(), new SingleEventObserver<Boolean>(this, alarmDialogLiveData) {
            @Override
            public void onChanged(Boolean aBoolean) {
                super.onChanged(aBoolean);
                if (aBoolean) {
                    Navigation.findNavController(mBinding.getRoot()).navigate(R.id.chooseAlarmFragment);
                }
            }
        });

        LiveData<Boolean> locatorLiveData = mAccountViewModel.getLocatorClicked();
        locatorLiveData.observe(getViewLifecycleOwner(), new SingleEventObserver<Boolean>(this, locatorLiveData) {
            @Override
            public void onChanged(Boolean aBoolean) {
                super.onChanged(aBoolean);
                if (aBoolean) {
                    Navigation.findNavController(mBinding.getRoot()).navigate(R.id.locatorFragment);
                }
            }
        });
    }
}