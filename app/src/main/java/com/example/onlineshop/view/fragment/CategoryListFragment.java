package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.CategoryAdapter;
import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.databinding.FragmentCategoryListBinding;
import com.example.onlineshop.view.observers.SingleEventObserver;
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

//        NavController navController = NavHostFragment.findNavController(this);
//        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                navController.popBackStack();
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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
        navListener();

        return mBinding.getRoot();
    }

    private void navListener() {
        LiveData<String> navigateLiveData = mCategoryListViewModel.getNavigateLiveData();
        navigateLiveData.observe(getViewLifecycleOwner(),
                new SingleEventObserver<String>(this, navigateLiveData) {
            @Override
            public void onChanged(String name) {
//                super.onChanged(s);
                if(name != null){
//                    Bundle bundle = new Bundle();
//                    bundle.putString("categoryName", name);
                    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.productListFragment, true).build();
                    Navigation.findNavController(mBinding.getRoot()).navigate(
                            CategoryListFragmentDirections.actionCategoryListFragmentToProductListFragment(name), navOptions);
                }
            }
        });
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