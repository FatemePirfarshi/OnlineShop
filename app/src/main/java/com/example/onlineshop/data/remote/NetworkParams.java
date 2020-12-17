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

    public static Map<String, String> getProductsOptions(int page) {
        Map<String, String> productOptions = new HashMap<>();
        productOptions.putAll(BASE_OPTIONS);
//        productOptions.put("page", String.valueOf(page));
        return productOptions;
    }

    public static Map<String, String> getPageOptions() {
        Map<String, String> pageOptions = new HashMap<>();
        pageOptions.put("rel", "next");
        return pageOptions;
    }

    public static Map<String, String> getCategoryOptions(int page) {
        Map<String, String> categoryOptions = new HashMap<>();
        categoryOptions.putAll(BASE_OPTIONS);
        categoryOptions.put("page", String.valueOf(page));
        return categoryOptions;
    }
}
