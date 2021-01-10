package com.example.onlineshop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemImageSliderBinding;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderHolder> {

    public static final String TAG = "SliderAdapter";
    private Context mContext;
    private List<String> mImages = new ArrayList<>();

    public SliderAdapter(Context context, ArrayList<String> itemImages) {
        Log.e(TAG, "adapter of slider called");
        mContext = context;
        Log.e(TAG, itemImages.get(0));

        mImages.addAll(itemImages);
    }

    @Override
    public SliderHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_image_slider,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(SliderHolder holder, int position) {
        String imageUrl = mImages.get(position);
        holder.bindProductImage(imageUrl);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    class SliderHolder extends SliderViewAdapter.ViewHolder {

        private ItemImageSliderBinding mBinding;

        public SliderHolder(ItemImageSliderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProductImage(String imageUrl) {
            Log.e(TAG, imageUrl);
//            Picasso.get()
//                    .load(imageUrl)
//                    .into(mBinding.ivProductImage);
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(mBinding.ivProductImage);

        }
    }
}
