package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.R;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.work.PollWorker;

public class ChooseAlarmViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mToggleClick = new MutableLiveData<>();
    private MutableLiveData<Boolean> mDismissDialog = new MutableLiveData<>();

    public ChooseAlarmViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getToggleClick() {
        return mToggleClick;
    }

    public MutableLiveData<Boolean> getDismissDialog() {
        return mDismissDialog;
    }

    public void togglePolling() {
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        PollWorker.enqueueWork(getApplication(), !isOn, 3);
        mToggleClick.setValue(true);
    }
    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }

    public void setAlarmForThreeHour(){
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        PollWorker.enqueueWork(getApplication(), isOn, 3);
        QueryPreferences.setLastAlarmChoose(getApplication(),3);
        mDismissDialog.setValue(true);
    }

    public void setAlarmForFiveHour(){
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        PollWorker.enqueueWork(getApplication(), isOn, 5);
        QueryPreferences.setLastAlarmChoose(getApplication(),5);
        mDismissDialog.setValue(true);
    }

    public void setAlarmForEightHour(){
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        PollWorker.enqueueWork(getApplication(), isOn, 8);
        QueryPreferences.setLastAlarmChoose(getApplication(),8);
        mDismissDialog.setValue(true);
    }

    public void setAlarmForTwelveHour(){
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        PollWorker.enqueueWork(getApplication(), isOn, 12);
        QueryPreferences.setLastAlarmChoose(getApplication(),12);
        mDismissDialog.setValue(true);
    }

    public int setAlarmPic(){
        if(isTaskScheduled())
            return R.drawable.ic_deactive_alarm;
        else
            return R.drawable.ic_active_alarm;
    }
}
