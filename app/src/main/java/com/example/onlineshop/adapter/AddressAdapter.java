package com.example.onlineshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemAddressListBinding;
import com.example.onlineshop.viewmodel.AccountViewModel;
import com.example.onlineshop.viewmodel.ProductPageViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressListHolder> {

    private Set<String> mAddresses;
    private List<String> mListAddresses = new ArrayList<>();
    private AccountViewModel mViewModel;

    public AddressAdapter(Set<String> addresses, AccountViewModel viewModel){
        mAddresses = addresses;
        for (String address: mAddresses) {
            mListAddresses.add(address);
        }
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public AddressListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressListHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_address_list,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressListHolder holder, int position) {

        holder.bindAddressItem(mListAddresses.get(position));
    }

    @Override
    public int getItemCount() {
        return mListAddresses.size();
    }

    public class AddressListHolder extends RecyclerView.ViewHolder {

        private final ItemAddressListBinding mBinding;

        public AddressListHolder(ItemAddressListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setAccountViewModel(mViewModel);
        }

        public void bindAddressItem(String address) {
            mBinding.setAddress(address);
        }
    }
}
