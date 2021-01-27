package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.repository.ProductRepository;
import com.example.onlineshop.work.PollWorker;

public class AccountViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mAlarmDialogStart = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLocatorClicked = new MutableLiveData<>();
    private ProductRepository mRepository;

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

    public void alarmClicked() {
        mAlarmDialogStart.setValue(true);
    }

    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }

    public void signInClick(){
        Customer customer = new Customer();
        mRepository.postCustomer(customer);
    }

    public void locationClicked(){
        mLocatorClicked.setValue(true);
    }

    public void startLocate(){
        //todo
    }
}
