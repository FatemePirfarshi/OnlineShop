package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.ProductItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface WoocommerceService {

    @GET("products/")
    Call<List<ProductItem>> listProductItems(
//            @HeaderMap Map<String, String> pages,
            @QueryMap Map<String, String> options
    );

    @GET("products/{id}")
    Call<ProductItem> getProductItem(
            @Path("id") Integer id,
            @QueryMap Map<String, String> options
    );

    @GET("products/categories/")
    Call<List<CategoryItem>> listCategoryItems(
            @QueryMap Map<String, String> options
    );

//    @Headers("Content-Type: application/json")
//    @POST("customers")
//    Call<Customer> createCustomer(
//            @Body Customer customer
//            );

    @FormUrlEncoded
    @POST("customers")
    Call<Customer> postCustomer(
            @Field("email") String email,
            @QueryMap Map<String,String> options);

    @FormUrlEncoded
    @POST("orders")
    Call<Customer> postOrder(
            @Field("email") String email,
            @QueryMap Map<String,String> options);
}
