package com.example.onlineshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshop.R;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.ItemCartListBinding;
import com.example.onlineshop.viewmodel.CartViewModel;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private CartViewModel mViewModel;

    public CartAdapter(CartViewModel viewModel){
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_cart_list,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        ProductItem item = mViewModel.getCartProductItem().getValue().get(position);
        holder.bindCartItem(item, position);
    }

    @Override
    public int getItemCount() {
        return mViewModel.getCartProductItem().getValue().size();
    }

    class CartHolder extends RecyclerView.ViewHolder{

        private ItemCartListBinding mBinding;

        public CartHolder(ItemCartListBinding binding) {
            super(binding.getRoot());
            mBinding= binding;
            mBinding.executePendingBindings();

            mBinding.setCartProductViewModel(mViewModel);
        }

        public void bindCartItem(ProductItem item, int position){
            mBinding.setPosition(position);

            Glide.with(mViewModel.getApplication())
                    .load(item.getImages().get(0))
                    .into(mBinding.ivProductImage);
        }
    }
}
