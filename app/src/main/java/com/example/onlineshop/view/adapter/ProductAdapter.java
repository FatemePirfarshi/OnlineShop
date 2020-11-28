package com.example.onlineshop.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.ItemProductListBinding;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<ProductItem> mItems;
    private Context mContext;

    public List<ProductItem> getItems() {
        return mItems;
    }

    public void setItems(List<ProductItem> items) {
        mItems = items;
    }

    public ProductAdapter(Context context, List<ProductItem> items){
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_product_list,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        ProductItem item = mItems.get(position);
        holder.bindProductItem(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        private ItemProductListBinding mBinding;

        public ProductHolder(ItemProductListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProductItem(ProductItem item){
            mBinding.titleProduct.setText(item.getProductName());
            mBinding.priceProduct.setText(item.getProductPrice());
        }
    }
}
