package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentChooseAlarmBinding;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.ChooseAlarmViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ChooseAlarmFragment extends BottomSheetDialogFragment {

    private FragmentChooseAlarmBinding mBinding;
    private ChooseAlarmViewModel mViewModel;

    public ChooseAlarmFragment() {
        // Required empty public constructor
    }

    public static ChooseAlarmFragment newInstance() {
        ChooseAlarmFragment fragment = new ChooseAlarmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ChooseAlarmViewModel.class);
        setLiveDataObservers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_choose_alarm,
                container,
                false);

        mBinding.setChooseAlarmViewModel(mViewModel);
        checkAlarmTime(QueryPreferences.getLastAlarmChoose(getActivity()));
        return mBinding.getRoot();
    }

    private void setLiveDataObservers() {
        mViewModel.getToggleClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(mViewModel.isTaskScheduled())
                    mBinding.ivSilentAlarm.setImageResource(R.drawable.ic_active_alarm);
                else
                    mBinding.ivSilentAlarm.setImageResource(R.drawable.ic_deactive_alarm);
            }
        });
        LiveData<Boolean> dismissDialogLiveData = mViewModel.getDismissDialog();
        dismissDialogLiveData.observe(this, new SingleEventObserver<Boolean>(
                this, dismissDialogLiveData) {
            @Override
            public void onChanged(Boolean aBoolean) {
                super.onChanged(aBoolean);
                if (aBoolean)
                    dismiss();
            }
        });
    }

    private void checkAlarmTime(Integer integer) {
        switch (integer){
            case 3:
                mBinding.btnThree.setChecked(true);
                break;
            case 5:
                mBinding.btnFive.setChecked(true);
                break;
            case 8:
                mBinding.btnEight.setChecked(true);
                break;
            case 12:
                mBinding.btnTwelve.setChecked(true);
                break;
        }
    }
}
