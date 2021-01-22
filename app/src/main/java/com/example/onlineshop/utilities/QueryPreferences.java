package com.example.onlineshop.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.onlineshop.data.model.ProductItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryPreferences {

    private static final String PREF_CART_PRODUCT = "cartProduct";

    public static void setCartProducts(Context context, List<ProductItem> productItems){

        Gson gson = new Gson();
        String jsonProducts = gson.toJson(productItems);

        getSharedPreferences(context)
                .edit()
                .putString(PREF_CART_PRODUCT, jsonProducts)
                .apply();
    }

    public static void addCartProduct(Context context, ProductItem item){
        List<ProductItem> productItems = getCartProducts(context);
        if(productItems == null)
            productItems = new ArrayList<>();
        productItems.add(item);
        setCartProducts(context, productItems);
    }

    public static void removeCartProduct(Context context, ProductItem item){
        ArrayList<ProductItem> productItems = getCartProducts(context);
        if(productItems != null){
            productItems.remove(productItems);
            setCartProducts(context, productItems);
        }
    }

    public static ArrayList<ProductItem> getCartProducts(Context context){

        if(getSharedPreferences(context).contains(PREF_CART_PRODUCT)){
            String jsonProducts = getSharedPreferences(context).getString(PREF_CART_PRODUCT, null);
            Gson gson = new Gson();
            ProductItem[] productItems = gson.fromJson(jsonProducts, ProductItem[].class);
            return new ArrayList<>(Arrays.asList(productItems));
        }
        return null;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}