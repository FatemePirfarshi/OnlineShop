package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.repository.CustomerRepository;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.work.PollWorker;

import java.util.List;
import java.util.Set;

public class AccountViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mAlarmDialogStart = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLocatorClicked = new MutableLiveData<>();
    private MutableLiveData<Boolean> mRequestPermission = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoginClicked = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoginAccount = new MutableLiveData<>();
    private MutableLiveData<Boolean> mShowAddressList = new MutableLiveData<>();
    private MutableLiveData<String> mClickedAddress = new MutableLiveData<>();

    private MutableLiveData<Set<String>> mAddressListLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mEmailLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mUserNameLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mAddressLiveData;
    private MutableLiveData<Integer> mCustomerResponseCode;

    private CustomerRepository mRepository;
    private LiveData<Customer> mCustomerLiveData;
    private LiveData<Boolean> mRegisterLiveData;
    private LiveData<Customer> mSearchCustomer;

    private String mEmail;
    private String mLoginEmail;
    private String mUserName;
    private String mCurrentAddress;

    public void fetchCurrentUser(){
        mAddressLiveData.setValue(QueryPreferences.getCurrentAddressQuery(getApplication()));
        mEmailLiveData.setValue(QueryPreferences.getEmailQuery(getApplication()));
        mUserNameLiveData.setValue(QueryPreferences.getUserNameQuery(getApplication()));
    }

    public LiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public LiveData<Boolean> getRegisterLiveData() {
        return mRegisterLiveData;
    }

    public MutableLiveData<Set<String>> getAddressListLiveData() {
        return mAddressListLiveData;
    }

    public MutableLiveData<Boolean> getShowAddressList() {
        return mShowAddressList;
    }

    public MutableLiveData<String> getClickedAddress() {
        return mClickedAddress;
    }

    public MutableLiveData<String> getEmailLiveData() {
        return mEmailLiveData;
    }

    public MutableLiveData<String> getUserNameLiveData() {
        return mUserNameLiveData;
    }

    public MutableLiveData<Integer> getCustomerResponseCode() {
        return mCustomerResponseCode;
    }

    public AccountViewModel(@NonNull Application application) {
        super(application);
        mRepository = CustomerRepository.getInstance();
        mCustomerLiveData = mRepository.getCustomerLiveData();
        mRegisterLiveData = mRepository.getRegisterLiveData();
        mSearchCustomer = mRepository.getSearchEmailLiveData();
        mAddressLiveData = mRepository.getCurrentAddressLiveData();
        mCustomerResponseCode = mRepository.getCustomerResponseCode();
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

    public MutableLiveData<String> getAddressLiveData() {
        return mAddressLiveData;
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
    }

    public void signInClick() {
        Customer customer = new Customer(mEmail);
        if (mUserName != null){
            customer.setUserName(mUserName);
            QueryPreferences.setUserNameQuery(getApplication(), mUserName);
        }
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

    public void onTextChangedAddress(CharSequence charSequence, int i, int i1, int i2) {
        mCurrentAddress = charSequence.toString();
//        mAddressLiveData.setValue(charSequence.toString());
    }

    public void addAddressClicked(){
        QueryPreferences.addUserAddress(getApplication(), mCurrentAddress);
    }

    public void setAddressItems() {
        Set<String> addressItems = QueryPreferences.getUserAddresses(getApplication());
        if (addressItems != null)
            mAddressListLiveData.setValue(addressItems);
    }

    public void showAddressList(){
        mShowAddressList.setValue(true);
    }

    public void addressClicked(String address){
        QueryPreferences.setCurrentAddressQuery(getApplication(), address);
        mClickedAddress.setValue(address);
        mRepository.getCurrentAddress(address);
        //callback
//        mAddressLiveData.setValue(address);
    }

    public void setCustomerSettingLiveData(){
        mRepository.setCustomerSettingLiveData();
    }
}
