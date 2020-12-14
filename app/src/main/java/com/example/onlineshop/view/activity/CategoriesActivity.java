package com.example.onlineshop.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityCategoriesBinding;
import com.example.onlineshop.view.fragment.ProductListFragment;
import com.example.onlineshop.viewmodel.ProductListViewModel;
import com.google.android.material.navigation.NavigationView;

public class CategoriesActivity extends AppCompatActivity {

    public static final String TAG = "CategoriesActivity";
    private ActivityCategoriesBinding mBinding;
    private ProductListViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_categories);

        if(savedInstanceState ==null) {
            addFragment(0);
//            mBinding.nvDrawer.setCheckedItem(R.id.);
        }

        mViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        mBinding.setProductListViewModel(mViewModel);

        setItemListener();

        mViewModel.getOpenedLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    if (!mBinding.drawerLayoutCategories.isDrawerOpen(GravityCompat.START))
                        mBinding.drawerLayoutCategories.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void setItemListener() {
        mBinding.nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.list_of_latest:
                        Log.d(TAG, " Latest item clicked");
                        addFragment(0);
                        break;
                    case R.id.list_of_popular:
                        Log.d(TAG, " Popular item clicked");
                        addFragment(1);
                        break;
                    case R.id.list_of_most_visit:
                        Log.d(TAG, " Most Visited item clicked");
                        addFragment(2);
                        break;
                }
                mBinding.drawerLayoutCategories.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void addFragment(int state) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ProductListFragment.newInstance(state))
                .commit();
    }
}