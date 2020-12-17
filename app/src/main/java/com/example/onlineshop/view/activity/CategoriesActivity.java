package com.example.onlineshop.view.activity;

import androidx.fragment.app.Fragment;

import com.example.onlineshop.view.fragment.CategoryListFragment;

public class CategoriesActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return CategoryListFragment.newInstance();
    }
}