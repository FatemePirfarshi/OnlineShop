package com.example.onlineshop.data.remote;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {

    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_0cc1aa2f0b4ec228a330d398dcf860db9d3da52d";
    public static final String CONSUMER_SECRET = "cs_70402dac0bf2c9580039977594684829962c669c";

    public static final String POPULAR = "popularity";
    public static final String RECENT = "date";
    public static final String TOP = "rating";

    public static final Map<String, String> BASE_OPTIONS = new HashMap<String, String>() {{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);
    }};

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

    public static Map<String, String> getBaseOptions() {
        Map<String, String> totalOptions = new HashMap<>();
        totalOptions.putAll(BASE_OPTIONS);
        return totalOptions;
    }

    public static Map<String, String> getHomeProductOptions(int perPage, String position){
        Map<String, String> options = new HashMap<>();
        options.putAll(BASE_OPTIONS);
        options.put("per_page", String.valueOf(perPage));
        options.put("orderby", position);
        return options;
    }

    public static Map<String, String> getSearchOptions(String query){
        Map<String, String> searchOptions = new HashMap<>();
        searchOptions.putAll(BASE_OPTIONS);
        searchOptions.put("search", query);
        return searchOptions;
    }
//    public static Map<String, String> getProductItemOptions(){
//        Map<String, String> productItemOptions = new HashMap<>();
//        productItemOptions.putAll(BASE_OPTIONS);
//    }
}
