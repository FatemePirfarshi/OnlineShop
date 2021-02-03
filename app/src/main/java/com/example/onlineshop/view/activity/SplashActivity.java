package com.example.onlineshop.view.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivitySplashBinding;
import com.example.onlineshop.viewmodel.HomeViewModel;

public class SplashActivity extends AppCompatActivity {

    private HomeViewModel mHomeViewModel;
    private ActivitySplashBinding mBinding;

//    private Boolean recentFlag;
//    private Boolean topFlag;
//    private Boolean popularFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

//        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//        mHomeViewModel.fetchTotalProducts();
//        mBinding.lottieDotsPreloader.playAnimation();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                mHomeViewModel.fetchTotalProducts();

                startActivity(HostActivity.newIntent(SplashActivity.this));
                finish();
            }
        }, 6000);


//        mHomeViewModel.getRecentItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
//            @Override
//            public void onChanged(List<ProductItem> productItems) {
//                recentFlag = true;
//                checkItems();
//
//            }
//        });
//
//        mHomeViewModel.getTopItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
//            @Override
//            public void onChanged(List<ProductItem> productItems) {
//                topFlag = true;
//                checkItems();
//            }
//        });
//
//        mHomeViewModel.getPopularItemsLiveData().observe(this, new Observer<List<ProductItem>>() {
//            @Override
//            public void onChanged(List<ProductItem> productItems) {
//                popularFlag = true;
//                checkItems();
//            }
//        });

    }
//
//    private void checkItems() {
//        if(topFlag && recentFlag && popularFlag){
//            startActivity(HostActivity.newIntent(SplashActivity.this));
//            finish();
//        }
//    }
}