package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.repository.ProductRepository;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.work.PollWorker;

public class AccountViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mAlarmDialogStart = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLocatorClicked = new MutableLiveData<>();
    private MutableLiveData<Boolean> mRequestPermission = new MutableLiveData<>();
    private ProductRepository mRepository;
    private String mEmail;
    private String mUserName;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance();
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

    public void alarmClicked() {
        mAlarmDialogStart.setValue(true);
    }

    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }

    public void onTextChangedEmail(CharSequence charSequence, int i, int i1, int i2){
        mEmail = charSequence.toString();
    }

    public void onTextChangedUserName(CharSequence charSequence, int i, int i1, int i2){
        mUserName = charSequence.toString();
    }

    public void signInClick() {
        Customer customer = new Customer();
        customer.setEmail(mEmail);
        customer.setUserName(mUserName);
        QueryPreferences.setEmailQuery(getApplication(), mEmail);
        mRepository.postCustomer(mEmail);
    }

    public void locationClicked() {
        mLocatorClicked.setValue(true);
    }

    public void startLocate() {
        mRequestPermission.setValue(true);
    }

}
