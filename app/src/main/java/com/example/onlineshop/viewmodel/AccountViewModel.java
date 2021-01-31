package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.repository.CustomerRepository;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.work.PollWorker;

public class AccountViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mAlarmDialogStart = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLocatorClicked = new MutableLiveData<>();
    private MutableLiveData<Boolean> mRequestPermission = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoginClicked = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoginAccount = new MutableLiveData<>();

    private CustomerRepository mRepository;
    private LiveData<Customer> mCustomerLiveData;
    private LiveData<Boolean> mRegisterLiveData;
    private LiveData<Customer> mSearchCustomer;

    private String mEmail;
    private String mLoginEmail;
    private String mUserName;

    public LiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public LiveData<Boolean> getRegisterLiveData() {
        return mRegisterLiveData;
    }

    public AccountViewModel(@NonNull Application application) {
        super(application);
        mRepository = CustomerRepository.getInstance();
        mCustomerLiveData = mRepository.getCustomerLiveData();
        mRegisterLiveData = mRepository.getRegisterLiveData();
        mSearchCustomer = mRepository.getSearchEmailLiveData();
    }

    public MutableLiveData<Boolean> getAlarmDialogStart() {
        return mAlarmDialogStart;
    }

    public MutableLiveData<Boolean> getLocatorClicked() {
        return mLocatorClicked;
    }

    public MutableLiveData<Boolean> getRequestPermission() {
        return mRequestPermission;
    }

    public MutableLiveData<Boolean> getLoginClicked() {
        return mLoginClicked;
    }

    public MutableLiveData<Boolean> getLoginAccount() {
        return mLoginAccount;
    }

    public LiveData<Customer> getSearchCustomer() {
        return mSearchCustomer;
    }

    public void alarmClicked() {
        mAlarmDialogStart.setValue(true);
    }

    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }

    public void onTextChangedEmail(CharSequence charSequence, int i, int i1, int i2) {
        mEmail = charSequence.toString();
    }

    public void onTextChangedUserName(CharSequence charSequence, int i, int i1, int i2) {
        mUserName = charSequence.toString();
    }

    public void onTextChangedLoginEmail(CharSequence charSequence, int i, int i1, int i2) {
        mLoginEmail = charSequence.toString();
    }

    public void getLogin() {
        mRepository.searchCustomer(mLoginEmail);
        mLoginAccount.setValue(true);
//        QueryPreferences.setEmailQuery();
    }
//    public void signInClick() {
//        Customer customer = new Customer();
//        customer.setEmail(mEmail);
//        customer.setUserName(mUserName);
//        QueryPreferences.setEmailQuery(getApplication(), mEmail);
//        mRepository.postCustomer(mEmail);
//    }

    public void signInClick() {
        Customer customer = new Customer(mEmail);
        if (mUserName != null)
            customer.setUserName(mUserName);
        QueryPreferences.setEmailQuery(getApplication(), mEmail);
        mRepository.createCustomer(customer);
    }

    public void loginClicked() {
        mLoginClicked.setValue(true);
    }

    public void locationClicked() {
        mLocatorClicked.setValue(true);
    }

    public void startLocate() {
        mRequestPermission.setValue(true);
    }

}
