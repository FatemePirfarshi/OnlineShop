package com.example.onlineshop.data.remote;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {

    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_0cc1aa2f0b4ec228a330d398dcf860db9d3da52d";
    public static final String CONSUMER_SECRET = "cs_70402dac0bf2c9580039977594684829962c669c";

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

    public static Map<String, String> getTotalProductsOptions() {
        Map<String, String> totalOptions = new HashMap<>();
        totalOptions.putAll(BASE_OPTIONS);
        return totalOptions;
    }

    public static Map<String, String> getPopularOptions(int perPage) {
        Map<String, String> popularOptions = new HashMap<>();
        popularOptions.putAll(BASE_OPTIONS);
        popularOptions.put("per_page", String.valueOf(perPage));
        popularOptions.put("orderby","popularity");
        return popularOptions;
    }

    public static Map<String, String> getRecentOptions(int perPage) {
        Map<String, String> popularOptions = new HashMap<>();
        popularOptions.putAll(BASE_OPTIONS);
        popularOptions.put("per_page", String.valueOf(perPage));
        popularOptions.put("orderby","date");
        return popularOptions;
    }

    public static Map<String, String> getTopOptions(int perPage) {
        Map<String, String> popularOptions = new HashMap<>();
        popularOptions.putAll(BASE_OPTIONS);
        popularOptions.put("per_page", String.valueOf(perPage));
        popularOptions.put("orderby","rating");
        return popularOptions;
    }
}
