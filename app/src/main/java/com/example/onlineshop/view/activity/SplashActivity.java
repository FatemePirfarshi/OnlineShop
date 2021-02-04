package com.example.onlineshop.view.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.databinding.ActivitySplashBinding;
import com.example.onlineshop.viewmodel.HomeViewModel;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private HomeViewModel mHomeViewModel;
    private ActivitySplashBinding mBinding;
    private boolean mNetworkConnect;

//    private Boolean recentFlag;
//    private Boolean topFlag;
//    private Boolean popularFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        mNetworkConnect = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(!mNetworkConnect){
            mBinding.tvNoInternet.setVisibility(View.VISIBLE);
            mBinding.lottieDotsPreloader.setVisibility(View.GONE);

        } else {
            mHomeViewModel.fetchTotalProducts();
            mBinding.lottieDotsPreloader.playAnimation();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                mHomeViewModel.fetchTotalProducts();
                    startActivity(HostActivity.newIntent(SplashActivity.this));
                    finish();
                }
            }, 50000);
        }

        mHomeViewModel.getPerPage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mHomeViewModel.fetchPopularItems(integer);
                mHomeViewModel.fetchRecentItems(integer);
                mHomeViewModel.fetchTopItems(integer);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    //        mHomeViewModel.getRecentItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
//            @Override
//            public void onChanged(List<ProductItem> productItems) {
//                recentFlag = true;
////                checkItems();
//
//            }
//        });
//
//        mHomeViewModel.getTopItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
//            @Override
//            public void onChanged(List<ProductItem> productItems) {
//                topFlag = true;
////                checkItems();
//            }
//        });
//
//        mHomeViewModel.getPopularItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
//            @Override
//            public void onChanged(List<ProductItem> productItems) {
//                popularFlag = true;
////                checkItems();
//            }
//        });


//    private void checkItems() {
//        if(topFlag || recentFlag || popularFlag){
//            startActivity(HostActivity.newIntent(SplashActivity.this));
//            finish();
//        }
//    }

}