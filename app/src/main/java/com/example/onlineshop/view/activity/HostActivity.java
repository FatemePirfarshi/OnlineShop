package com.example.onlineshop.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityHostBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HostActivity extends AppCompatActivity {

    private ActivityHostBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_host);

        setNavController();
    }

    private void setNavController() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBinding.bottomNavigation, navController);

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.categoryListFragment:
                                Navigation.findNavController(
                                        HostActivity.this,
                                        R.id.nav_host_fragment).navigate(R.id.categoryListFragment);
                                break;
                            case R.id.homeFragment:
                                Navigation.findNavController(
                                        HostActivity.this,
                                        R.id.nav_host_fragment).navigate(R.id.homeFragment);
                                break;
                            case R.id.cartFragment:
                                Navigation.findNavController(
                                        HostActivity.this,
                                        R.id.nav_host_fragment).navigate(R.id.cartFragment);
                        }
                        return true;
                    }
                });
    }
}