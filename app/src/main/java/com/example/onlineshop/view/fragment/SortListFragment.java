package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSortListBinding;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.SortListViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortListFragment extends BottomSheetDialogFragment {

    private FragmentSortListBinding mBinding;
    private SortListViewModel mViewModel;
    private int page = 1;

    public SortListFragment() {
        // Required empty public constructor
    }

    public static SortListFragment newInstance() {
        SortListFragment fragment = new SortListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SortListViewModel.class);
        mViewModel.fetchTotalProductsForCategory();
        setLiveDataObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_sort_list,
                container,
                false);
        mBinding.setSortListViewModel(mViewModel);

        return mBinding.getRoot();
    }

    private void setLiveDataObserver() {
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
}