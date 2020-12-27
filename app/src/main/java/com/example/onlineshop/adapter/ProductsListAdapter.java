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

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductListHolder> {

    private ProductListViewModel mViewModel;
    private List<ProductItem> mProductItems;

    public ProductsListAdapter(ProductListViewModel viewModel, List<ProductItem> productItems) {
        mViewModel = viewModel;
        mProductItems = productItems;
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
        holder.bindTopProductItem(position, item);
    }

    @Override
    public int getItemCount() {
        return mProductItems.size();
    }

    class ProductListHolder extends RecyclerView.ViewHolder {

        private ItemProductListBinding mBinding;

        public ProductListHolder(ItemProductListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setProductListViewModel(mViewModel);

            List<ProductItem> items = mProductItems;
            List<String> productsName = new ArrayList<>();
            List<String> productsPrice = new ArrayList<>();

            for (int i = 0; i < items.size(); i++) {
                productsName.add(items.get(i).getProductName());
                productsPrice.add(items.get(i).getProductPrice());
            }
            mBinding.setProductNameList(productsName);
            mBinding.setProductPriceList(productsPrice);
        }

        public void bindTopProductItem(int position, ProductItem item) {
            mBinding.setPosition(position);

            Picasso.get()
                    .load(item.getImages().get(0))
                    .into(mBinding.imageProduct);
        }
    }
}
