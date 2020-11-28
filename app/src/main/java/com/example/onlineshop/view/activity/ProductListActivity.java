package com.example.onlineshop.view.activity;

import androidx.fragment.app.Fragment;

import com.example.onlineshop.view.fragment.ProductListFragment;

public class ProductListActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return ProductListFragment.newInstance();
    }
}