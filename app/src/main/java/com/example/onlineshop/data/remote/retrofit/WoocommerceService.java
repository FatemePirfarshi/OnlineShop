package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.CategoryItem;
import com.example.onlineshop.data.model.ProductItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface WoocommerceService {

    //    @GET("products/")
//    Call<String> getPages(@Header("X-WP-TotalPages") String pages);
//    @Headers("Cache-Control: max-age=640000")
//    @Header("rel: next")
    @GET("products/")
    Call<List<ProductItem>> listProductItems(
//            @Header("Accept-Language") String lang,
//            @Header("X-WP-TotalPages") String pages,
            @HeaderMap Map<String, String> pages,
            @QueryMap Map<String, String> options
    );

//    @Headers("Cache-Control: max-age=640000")
//    @GET("/")
//    Call<List<ProductItem>> listProductItemsp(
////            @Header("X-WP-TotalPages") String pages,
////            @HeaderMap
//            @QueryMap Map<String, String> options
//    );

//    @Headers({
//            "X-Foo: Bar",
//            "X-Ping: Pong"
//    })
//    @GET("/")
//    Call<List<ProductItem>> listProductItemsPages(
////            @Header("X-WP-TotalPages") String pages,
////            @HeaderMap
//            @QueryMap Map<String, String> options
//    );

    @GET("products/{id}")
    Call<ProductItem> getProductItem(
            @Path("id") Integer id
    );

    @GET("products/categories/")
    Call<List<CategoryItem>> listCategoryItems(
            @QueryMap Map<String, String> options
    );
}
