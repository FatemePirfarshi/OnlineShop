package com.example.onlineshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.ItemProductListBinding;
import com.example.onlineshop.viewmodel.HomeViewModel;

public class PopularListAdapter extends RecyclerView.Adapter<PopularListAdapter.PopularListHolder> {

    private HomeViewModel mViewModel;

    public PopularListAdapter(HomeViewModel viewModel) {
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public PopularListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularListHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mViewModel.getApplication()),
                R.layout.item_product_list,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PopularListHolder extends RecyclerView.ViewHolder {

        private ItemProductListBinding mBinding;

        public PopularListHolder(ItemProductListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindPopularProductItem(ProductItem item) {

        }
    }
}
