package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.databinding.FragmentAccountBinding;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.AccountViewModel;

import java.util.Set;

public class AccountFragment extends VisibleFragment {

    private AccountViewModel mAccountViewModel;
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
        mAccountViewModel.setAddressItems();

        mAccountViewModel.fetchCurrentUser();
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
        mBinding.etAddress.setText(QueryPreferences.getCurrentAddressQuery(getActivity()));

//        setLiveDataObservers();
        navListener();

        return mBinding.getRoot();
    }


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

        LiveData<Boolean> loginLiveData = mAccountViewModel.getLoginClicked();
        loginLiveData.observe(getViewLifecycleOwner(), new SingleEventObserver<Boolean>(this, loginLiveData) {
            @Override
            public void onChanged(Boolean aBoolean) {
                super.onChanged(aBoolean);
                if (aBoolean) {
                    Navigation.findNavController(mBinding.getRoot()).navigate(R.id.loginFragment);
                }
            }
        });

        LiveData<Boolean> showAddressLiveData = mAccountViewModel.getShowAddressList();
        showAddressLiveData.observe(getViewLifecycleOwner(), new SingleEventObserver<Boolean>(this, showAddressLiveData) {
            @Override
            public void onChanged(Boolean aBoolean) {
                super.onChanged(aBoolean);
                if (aBoolean) {
                    Navigation.findNavController(mBinding.getRoot()).navigate(R.id.addressListFragment);
                }
            }
        });

        mAccountViewModel.getCustomerLiveData().observe(getViewLifecycleOwner(), new Observer<Customer>() {
            @Override
            public void onChanged(Customer customer) {
                if (customer != null) {
                    Toast.makeText(
                            getActivity(),
                            R.string.sign_in_successful,
                            Toast.LENGTH_LONG).show();
                    QueryPreferences.setEmailQuery(getActivity(), customer.getEmail());
                    QueryPreferences.setUserNameQuery(getActivity(), customer.getUserName());
                } else
                    Toast.makeText(
                            getActivity(),
                            R.string.invalidate_email_or_registered,
                            Toast.LENGTH_LONG).show();

                mAccountViewModel.setCustomerSettingLiveData();
            }
        });
        mAccountViewModel.getSearchCustomer().observe(getViewLifecycleOwner(), new Observer<Customer>() {
            @Override
            public void onChanged(Customer customer) {
                if (customer == null) {
                    Toast.makeText(
                            getActivity(),
                            R.string.invalidate_email,
                            Toast.LENGTH_LONG).show();
                } else {
                    QueryPreferences.setEmailQuery(getActivity(), customer.getEmail());
                    QueryPreferences.setUserNameQuery(getActivity(), customer.getUserName());
//                    QueryPreferences.setIdQuery(getActivity(), customer.getId());

                    mBinding.etEmail.setText(QueryPreferences.getEmailQuery(getActivity()));
                    mBinding.etUserName.setText(QueryPreferences.getUserNameQuery(getActivity()));
                }
                mAccountViewModel.setCustomerSettingLiveData();
            }
        });
        mAccountViewModel.getCustomerLiveData().observe(getViewLifecycleOwner(), new Observer<Customer>() {
            @Override
            public void onChanged(Customer customer) {
                if (customer != null) {
                    QueryPreferences.setEmailQuery(getActivity(), customer.getEmail());
                    QueryPreferences.setUserNameQuery(getActivity(), customer.getUserName());
                    if (customer.getId() != null)
                        QueryPreferences.setIdQuery(getActivity(), customer.getId());
                }
            }
        });

        mAccountViewModel.getAddressLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                QueryPreferences.setCurrentAddressQuery(getActivity(), s);
                mBinding.etAddress.setText(QueryPreferences.getCurrentAddressQuery(getActivity()));
            }
        });
    }
}