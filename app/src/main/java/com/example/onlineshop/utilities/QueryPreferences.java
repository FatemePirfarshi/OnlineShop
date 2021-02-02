package com.example.onlineshop.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.onlineshop.data.model.ProductItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueryPreferences {

    private static final String PREF_CART_PRODUCT = "cartProduct";
    private static final String PREF_LAST_PRODUCT_ID = "lastProductId";
    private static final String PREF_CUSTOMER_EMAIL = "userEmail";
    private static final String PREF_CUSTOMER_USERNAME = "userName";
    private static final String PREF_CUSTOMER_ID = "id";
    public static final String PREF_USER_ADDRESSES = "userAddresses";
    public static final String PREF_CURRENT_ADDRESS = "currentAddress";

    public static String getCurrentAddressQuery(Context context) {
        return getSharedPreferences(context).getString(PREF_CURRENT_ADDRESS, null);
    }

    public static void setCurrentAddressQuery(Context context, String address) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_CURRENT_ADDRESS, address)
                .apply();
    }

    public static Integer getIdQuery(Context context) {
        return getSharedPreferences(context).getInt(PREF_CUSTOMER_ID, 0);
    }

    public static void setIdQuery(Context context, Integer id) {
        getSharedPreferences(context)
                .edit()
                .putInt(PREF_CUSTOMER_ID, id)
                .apply();
    }

    public static String getEmailQuery(Context context) {
        return getSharedPreferences(context).getString(PREF_CUSTOMER_EMAIL, null);
    }

    public static void setEmailQuery(Context context, String email) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_CUSTOMER_EMAIL, email)
                .apply();
    }

    public static String getUserNameQuery(Context context) {
        return getSharedPreferences(context).getString(PREF_CUSTOMER_USERNAME, null);
    }

    public static void setUserNameQuery(Context context, String userName) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_CUSTOMER_USERNAME, userName)
                .apply();
    }

    public static int getLastProductId(Context context){
        return getSharedPreferences(context).getInt(PREF_LAST_PRODUCT_ID, 0);
    }

    public static void setLastProductId(Context context, int lastProductId){
        getSharedPreferences(context)
                .edit()
                .putInt(PREF_LAST_PRODUCT_ID, lastProductId)
                .apply();
    }

    public static void setUserAddress(Context context, Set<String> customerAddress){
        getSharedPreferences(context)
                .edit()
                .putStringSet(PREF_USER_ADDRESSES, customerAddress)
                .apply();
    }

    public static void addUserAddress(Context context, String address){
        Set<String> addressItems = getUserAddresses(context);
        if(addressItems == null)
            addressItems = new HashSet<>();
        addressItems.add(address);
        setUserAddress(context, addressItems);
    }

    public static Set<String> getUserAddresses(Context context){
        return getSharedPreferences(context).getStringSet(PREF_USER_ADDRESSES, null);
    }

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
