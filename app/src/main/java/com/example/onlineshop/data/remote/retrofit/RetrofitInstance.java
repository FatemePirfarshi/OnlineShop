package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.data.model.CouponLines;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.ProductItem;
import com.example.onlineshop.data.model.Review;
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

    public static Retrofit getCategoryInstance() {
        return new Retrofit.Builder()
                .baseUrl(NetworkParams.BASE_URL)
                .addConverterFactory(createGsonConverter(
                        new TypeToken<List<CategoryItem>>() {}.getType(),
                        new GetCategoryItemDeserializer()))
                .build();
    }

    public static Retrofit getProductInstance() {
        return new Retrofit.Builder()
                .baseUrl(NetworkParams.BASE_URL)
                .addConverterFactory(createGsonConverter(
                        new TypeToken<List<ProductItem>>() {}.getType(),
                        new GetProductItemDeserializer()))
                .build();
    }

    public static Retrofit getProductInstanceWithId(){
        return new Retrofit.Builder()
                .baseUrl(NetworkParams.BASE_URL)
                .addConverterFactory(createGsonConverter(
                        ProductItem.class,
                        new GetSingleProductItemDeserializer()
                ))
                .build();
    }

    public static Retrofit getCustomerInstance(){
        return new Retrofit.Builder()
                .baseUrl(NetworkParams.BASE_URL)
                .addConverterFactory(createGsonConverter(
                        new TypeToken<List<Customer>>() {}.getType(),
                        new GetCustomerDeserializer()
                ))
                .build();
    }

    public static Retrofit getReviewInstance(){
        return new Retrofit.Builder()
                .baseUrl(NetworkParams.BASE_URL)
                .addConverterFactory(createGsonConverter(
                        new TypeToken<List<Review>>() {}.getType(),
                        new GetReviewDeserializer()
                ))
                .build();
    }

    public static Retrofit getCouponInstance(){
        return new Retrofit.Builder()
                .baseUrl(NetworkParams.BASE_URL)
                .addConverterFactory(createGsonConverter(
                        new TypeToken<List<CouponLines>>() {}.getType(),
                        new GetCouponDeserializer()
                ))
                .build();
    }
//    public static Retrofit getCustomerInstance() {
//        return new Retrofit.Builder()
//                .baseUrl(NetworkParams.BASE_URL)
//                .addConverterFactory(createGsonConverter(
//                        new TypeToken<Customer>() {
//                        }.getType(),
//                        new GetCustomerSerializer()))
//                .build();
//    }

    private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
