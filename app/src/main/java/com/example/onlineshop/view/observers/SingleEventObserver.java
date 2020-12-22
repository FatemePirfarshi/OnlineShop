package com.example.onlineshop.view.observers;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public abstract class SingleEventObserver<T> implements Observer<T> {

    private LifecycleOwner mLifecycleOwner;
    private LiveData mLiveData;

    public SingleEventObserver(LifecycleOwner lifecycleOwner, LiveData liveData){
        mLifecycleOwner = lifecycleOwner;
        mLiveData = liveData;
    }

    @Override
    public void onChanged(T t) {
        mLiveData.removeObservers(mLifecycleOwner);
    }
}
