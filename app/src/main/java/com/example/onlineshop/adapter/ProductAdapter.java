package com.example.onlineshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.ItemProductListBinding;
import com.example.onlineshop.viewmodel.ProductListViewModel;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    public static final String TAG = "productAdapter";
    private ProductListViewModel mViewModel;

    public ProductAdapter(ProductListViewModel viewModel) {
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mViewModel.getApplication()),
                R.layout.item_product_list,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        ProductItem item = mViewModel.getCurrentItems().get(position);
        holder.bindProductItem(item);
    }

    @Override
    public int getItemCount() {
        return mViewModel.getCurrentItems().size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        private ItemProductListBinding mBinding;

        public ProductHolder(ItemProductListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }

        public void bindProductItem(ProductItem item) {
            mBinding.titleProduct.setText(item.getProductName());
            mBinding.priceProduct.setText("تومان " + item.getProductPrice());

            Picasso.get()
                    .load(item.getImages().get(0))
                    .into(mBinding.imageProduct);
        }
    }
}
