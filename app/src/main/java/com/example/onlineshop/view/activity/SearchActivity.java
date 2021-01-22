package com.example.onlineshop.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductsListAdapter;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.ActivitySearchBinding;
import com.example.onlineshop.view.observers.SingleEventObserver;
import com.example.onlineshop.viewmodel.ProductListViewModel;
import com.example.onlineshop.viewmodel.SearchViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivitySearchBinding mBinding;
    private SearchViewModel mViewModel;
    private ProductListViewModel mProductListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        mProductListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);

        handleIntent(getIntent());

        initViews();
        mViewModel.getSearchLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setupAdapter(productItems);
            }
        });
        navListener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e("SearchActivity" , "query for search: " + query);
            mViewModel.fetchSearchItems(query);
        }
    }

    private void initViews(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                true);
        mBinding.rvSearch.setLayoutManager(layoutManager);
    }

    private void setupAdapter(List<ProductItem> items) {
        ProductsListAdapter adapter = new ProductsListAdapter(this, mProductListViewModel,
                mViewModel.getSearchLiveData().getValue(), 0);
        mBinding.rvSearch.setAdapter(adapter);
    }

    private void navListener() {
        LiveData<Boolean> showDialogLiveData = mProductListViewModel.getSortDialogStart();
        showDialogLiveData.observe(this, new SingleEventObserver<Boolean>(this, showDialogLiveData) {
            @Override
            public void onChanged(Boolean aBoolean) {
                super.onChanged(aBoolean);
                if (aBoolean) {
                    Navigation.findNavController(mBinding.getRoot()).navigate(R.id.sortListFragment);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }

//    private void show(Fragment fragment) {
//
//        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        fragmentManager
//                .beginTransaction()
//                .replace(R.id.main_content, fragment)
//                .commit();
//
//        drawerLayout.closeDrawer(GravityCompat.START);
//    }
}