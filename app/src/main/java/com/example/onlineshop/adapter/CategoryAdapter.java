package com.example.onlineshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.databinding.ItemCategoryListBinding;
import com.example.onlineshop.viewmodel.CategoryListViewModel;
import com.squareup.picasso.Picasso;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private CategoryListViewModel mViewModel;

    public CategoryAdapter(CategoryListViewModel viewModel) {
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mViewModel.getApplication()),
                R.layout.item_category_list,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        CategoryItem item = mViewModel.getCurrentCategories().get(position);
        holder.bindCategoryItem(item);
    }

    @Override
    public int getItemCount() {
        return mViewModel.getCurrentCategories().size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        private ItemCategoryListBinding mBinding;

        public CategoryHolder(ItemCategoryListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindCategoryItem(CategoryItem item) {
            mBinding.titleCategory.setText(item.getName());
            mBinding.countProduct.setText(item.getCount() + " کالا ");

            Picasso.get()
                    .load(item.getImage())
                    .into(mBinding.imageCategory);
        }
    }
}