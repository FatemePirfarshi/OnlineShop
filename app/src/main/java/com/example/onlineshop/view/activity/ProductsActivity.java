package com.example.onlineshop.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentProductsListBinding;
import com.example.onlineshop.view.fragment.ProductListFragment;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.ProductListViewModel;
import com.google.android.material.navigation.NavigationView;

public class ProductsActivity extends AppCompatActivity {

    public static final String EXTRA_LIST_CATEGORY_NAME = "com.example.onlineshop.listCategoryName";

    public static void start(Context context, String categoryName) {
        Intent starter = new Intent(context, ProductsActivity.class);
        starter.putExtra(EXTRA_LIST_CATEGORY_NAME, categoryName);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    public static final String TAG = "CategoriesActivity";
    private FragmentProductsListBinding mBinding;
    private ProductListViewModel mViewModel;
    private String mListCategoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_products_list);
        mListCategoryName = getIntent().getStringExtra(EXTRA_LIST_CATEGORY_NAME);

        if (savedInstanceState == null) {
            addFragment(0);
//            mBinding.nvDrawer.setCheckedItem(R.id.);
        }

        mViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        //todo
        mBinding.setProductListViewModel(mViewModel);

        setItemListener();

        LiveData<Boolean> openedLiveData = mViewModel.getOpenedLiveData();
        openedLiveData.observe(this,
                new SingleEventObserver<Boolean>(this, openedLiveData) {
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
                        switch (item.getItemId()) {
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
                .replace(R.id.fragment_container, ProductListFragment.newInstance())
//                .replace(R.id.fragment_container, CategoryListFragment.newInstance())
                .commit();
    }
}