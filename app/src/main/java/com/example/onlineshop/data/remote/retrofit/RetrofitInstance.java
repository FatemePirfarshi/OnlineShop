package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.remote.NetworkParams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static Retrofit getInstance() {
        return new Retrofit.Builder()
                .baseUrl(NetworkParams.BASE_URL)
//                .addConverterFactory(createGsonConverter())
                .addConverterFactory(createGsonConverterCategories())
                .build();
    }

    private static Converter.Factory createGsonConverter() {
        Type type = new TypeToken<List<ProductItem>>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new GetProductItemDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }

    private static Converter.Factory createGsonConverterCategories() {
        Type type = new TypeToken<List<CategoryItem>>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new GetCategoryItemDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
