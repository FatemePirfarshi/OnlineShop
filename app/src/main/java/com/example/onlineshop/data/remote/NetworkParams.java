package com.example.onlineshop.data.remote;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {

    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_0cc1aa2f0b4ec228a330d398dcf860db9d3da52d";
    public static final String CONSUMER_SECRET = "cs_70402dac0bf2c9580039977594684829962c669c";

    public static final String POPULAR = "popularity";
    public static final String RECENT = "date";
    public static final String TOP = "rating";
    public static final String ORDERBY = "orderby";
    public static final String PRICE = "price";

    public static final Map<String, String> BASE_OPTIONS = new HashMap<String, String>() {{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);
    }};

    public static Map<String, String> getBaseOptions() {
        Map<String, String> totalOptions = new HashMap<>();
        totalOptions.putAll(BASE_OPTIONS);
        return totalOptions;
    }

    public static Map<String, String> getProductsOptions(int page, int categoryId) {
        Map<String, String> productOptions = new HashMap<>();
        productOptions.putAll(BASE_OPTIONS);
        productOptions.put("page", String.valueOf(page));
        productOptions.put("category", String.valueOf(categoryId));
        return productOptions;
    }

    public static Map<String, String> getCategoryOptions(int page) {
        Map<String, String> categoryOptions = new HashMap<>();
        categoryOptions.putAll(BASE_OPTIONS);
        categoryOptions.put("page", String.valueOf(page));
        return categoryOptions;
    }

    public static Map<String, String> getPerPageForCategory(int categoryId) {
        Map<String, String> perPageOptions = new HashMap<>();
        perPageOptions.putAll(BASE_OPTIONS);
        perPageOptions.put("category", String.valueOf(categoryId));
        return perPageOptions;
    }

    public static Map<String, String> getHomeProductOptions(int perPage, String position){
        Map<String, String> options = new HashMap<>();
        options.putAll(BASE_OPTIONS);
        options.put("per_page", String.valueOf(perPage));
        options.put(ORDERBY, position);
        return options;
    }

    public static Map<String, String> getSearchOptions(String query, int categoryId){
        Map<String, String> searchOptions = new HashMap<>();
        searchOptions.putAll(BASE_OPTIONS);
        searchOptions.put("search", query);
        searchOptions.put("category", String.valueOf(categoryId));
        return searchOptions;
    }

    public static Map<String, String> getCheapest(int perPage, int categoryId){
        Map<String, String> cheapestOptions = new HashMap<>();
        cheapestOptions.putAll(BASE_OPTIONS);
        cheapestOptions.put(ORDERBY, PRICE);
        cheapestOptions.put("per_page", String.valueOf(perPage));
        cheapestOptions.put("category", String.valueOf(categoryId));
        cheapestOptions.put("order", "asc");
        return cheapestOptions;
    }

    public static Map<String, String> getMostExpensive(int perPage, int categoryId, String position){
        Map<String, String> mostExpensiveOptions = new HashMap<>();
        mostExpensiveOptions.putAll(BASE_OPTIONS);
        mostExpensiveOptions.put(ORDERBY, position);
        mostExpensiveOptions.put("per_page", String.valueOf(perPage));
        mostExpensiveOptions.put("category", String.valueOf(categoryId));
        return mostExpensiveOptions;
    }

    public static Map<String, String> getCustomer(String email){
        Map<String, String> customerOptions = new HashMap<>();
        customerOptions.putAll(BASE_OPTIONS);
        customerOptions.put("email", email);
        return customerOptions;
    }

    public static Map<String, String> getReviews(int productId){
        Map<String, String> reviewOptions = new HashMap<>();
        reviewOptions.putAll(BASE_OPTIONS);
        reviewOptions.put("product", String.valueOf(productId));
        Log.e("productReview", String.valueOf(productId));
        return reviewOptions;
    }

    public static Map<String, String> getCouponOptions(String code){
        Map<String, String> couponOptions = new HashMap<>();
        couponOptions.putAll(BASE_OPTIONS);
        couponOptions.put("code", code);
        return couponOptions;
    }
    //    public static Map<String, String> getNewest(int perPage, int categoryId){
//        Map<String, String> newestOptions = new HashMap<>();
//        newestOptions.putAll(BASE_OPTIONS);
//        newestOptions.put(ORDERBY, RECENT);
//        newestOptions.put("per_page", String.valueOf(perPage));
//        newestOptions.put("category", String.valueOf(categoryId));
//        return newestOptions;
//    }
}
