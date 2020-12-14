package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.ProductItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface WoocommerceService {

//    @GET("products/")
//    Call<String> getPages(@Header("X-WP-TotalPages") String pages);

    @GET("products/")
    Call<List<ProductItem>> listProductItems(
//            @Header("X-WP-TotalPages") String pages,
            @QueryMap Map<String ,String> options
    );

    @GET("products/{id}")
    Call<ProductItem> getProductItem(
            @Path("id") Integer id
    );
}
