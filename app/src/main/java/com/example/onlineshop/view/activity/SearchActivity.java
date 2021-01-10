package com.example.onlineshop.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel mViewModel;

    private static final String JARGON = "appDataBoolean";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_search);

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
        if (appData != null) {
            boolean jargon = appData.getBoolean(SearchActivity.JARGON);
        }

        handleIntent(getIntent());
//        Intent intent = getIntent();
//        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
//            String query = intent.getStringExtra(SearchManager.QUERY);
//        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mViewModel.fetchSearchItems(query);
        }
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putBoolean(SearchActivity.JARGON, true);
        startSearch(null, false, appData, false);
        return true;
    }
//
//        @Override
//    public boolean onSearchRequested() {
//        return super.onSearchRequested();
//    }
}