package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentChooseAlarmBinding;
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
        return mBinding.getRoot();
    }
}
