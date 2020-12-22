package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.CategoryAdapter;
import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.databinding.FragmentCategoryListBinding;
import com.example.onlineshop.viewmodel.CategoryListViewModel;

import java.util.List;

public class CategoryListFragment extends Fragment {

    public static final String TAG = "CategoryList";
    private FragmentCategoryListBinding mBinding;
    private CategoryListViewModel mCategoryListViewModel;
    private int page = 1;

    public CategoryListFragment() {
        // Required empty public constructor
    }

    public static CategoryListFragment newInstance() {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryListViewModel = new ViewModelProvider(this).get(CategoryListViewModel.class);
        mCategoryListViewModel.fetchCategoryItemsAsync(page);

        setLiveDataObservers();
    }

    private void setLiveDataObservers() {
        mCategoryListViewModel.getListLiveData().observe(this, new Observer<List<CategoryItem>>() {
            @Override
            public void onChanged(List<CategoryItem> categoryItems) {
                setupAdapter(categoryItems);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_category_list,
                container,
                false);

        initViews();
        scrollListener();
        return mBinding.getRoot();
    }

    private void scrollListener() {
        mBinding.rvCategories.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (page <= mCategoryListViewModel.getPageCount().getValue())
                        mCategoryListViewModel.fetchCategoryItemsAsync(++page);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                dx = recyclerView.FOCUS_LEFT;

                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                true);
        layoutManager.setReverseLayout(true);
        mBinding.rvCategories.setLayoutManager(layoutManager);
//        mBinding.listTitle.setText(title);
    }

    private void setupAdapter(List<CategoryItem> items) {
        CategoryAdapter adapter = new CategoryAdapter(mCategoryListViewModel);
        mBinding.rvCategories.setAdapter(adapter);
    }
}