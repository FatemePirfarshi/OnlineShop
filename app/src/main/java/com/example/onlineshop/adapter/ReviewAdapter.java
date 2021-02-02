package com.example.onlineshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Review;
import com.example.onlineshop.databinding.ItemReviewListBinding;
import com.example.onlineshop.viewmodel.ProductPageViewModel;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewListHolder> {

    private ProductPageViewModel mViewModel;

    public ReviewAdapter(ProductPageViewModel productPageViewModel) {
        mViewModel = productPageViewModel;
    }

    @NonNull
    @Override
    public ReviewListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewListHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mViewModel.getApplication()),
                R.layout.item_review_list,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListHolder holder, int position) {
        Review review = mViewModel.getReviewsLiveData().getValue().get(position);
        holder.bindReviewItem(review, position);
    }

    @Override
    public int getItemCount() {
        return mViewModel.getReviewsLiveData().getValue().size();
    }

    public class ReviewListHolder extends RecyclerView.ViewHolder {

        private final ItemReviewListBinding mBinding;

        public ReviewListHolder(ItemReviewListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setProductPageViewModel(mViewModel);
        }

        public void bindReviewItem(Review reviewItem, int position) {
            mBinding.setPosition(position);
        }
    }
}
