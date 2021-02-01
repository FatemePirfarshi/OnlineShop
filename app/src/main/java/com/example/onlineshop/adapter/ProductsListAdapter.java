package com.example.onlineshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshop.R;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.ItemProductListBinding;
import com.example.onlineshop.viewmodel.ProductListViewModel;

import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductListHolder> {

    private ProductListViewModel mViewModel;
    private List<ProductItem> mProductItems;
    private int mListPosition;
    private LifecycleOwner mOwner;

    public ProductsListAdapter(LifecycleOwner owner, ProductListViewModel viewModel,
                               List<ProductItem> productItems, int listPosition) {
        mViewModel = viewModel;
        mProductItems = productItems;
        mListPosition = listPosition;
        mOwner = owner;

    }

    @NonNull
    @Override
    public ProductListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductListHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mViewModel.getApplication()),
                R.layout.item_product_list,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListHolder holder, int position) {
        ProductItem item = mProductItems.get(position);
        holder.bindProductItem(position, item);
    }

    @Override
    public int getItemCount() {
        return mProductItems.size();
    }

    public class ProductListHolder extends RecyclerView.ViewHolder {

        private ItemProductListBinding mBinding;

        public ProductListHolder(ItemProductListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setProductViewModel(mViewModel);
            mBinding.setLifecycleOwner(mOwner);

        }

        public void bindProductItem(int position, ProductItem item) {
            mBinding.setProductItem(item);

            if (item.getImages().size() > 0)
                Glide.with(mViewModel.getApplication())
                        .load(item.getImages().get(0))
                        .into(mBinding.imageProduct);
        }
    }
}
