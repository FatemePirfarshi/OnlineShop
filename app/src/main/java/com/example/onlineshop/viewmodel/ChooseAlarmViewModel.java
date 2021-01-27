package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.work.PollWorker;

public class ChooseAlarmViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mToggleClick = new MutableLiveData<>();
    public ChooseAlarmViewModel(@NonNull Application application) {
        super(application);
    }

    public void togglePolling() {
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        PollWorker.enqueueWork(getApplication(), !isOn);
        mToggleClick.setValue(true);
    }

}
