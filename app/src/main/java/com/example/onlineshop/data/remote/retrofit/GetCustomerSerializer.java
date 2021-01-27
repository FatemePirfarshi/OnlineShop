package com.example.onlineshop.data.remote.retrofit;

import android.util.Log;

import com.example.onlineshop.data.model.Billing;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.Shipping;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class GetCustomerSerializer implements JsonSerializer<Customer> {

    @Override
    public JsonElement serialize(Customer src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject customerObject = new JsonObject();

        customerObject.addProperty("first_name", src.getFirst_name());
        customerObject.addProperty("last_name", src.getLast_name());
        customerObject.addProperty("username", src.getUsername());
        customerObject.addProperty("email", src.getEmail());

        Log.e("postCustomer", "customer serialized");

        customerObject.add(
                "billing",
                new GetBillingSerializer().serialize(src.getBilling(), Billing.class, context));
        customerObject.add(
                "shipping",
                new GetShippingSerializer().serialize(src.getShipping(), Shipping.class, context));

        return customerObject;
    }

    class GetBillingSerializer implements JsonSerializer<Billing> {
        @Override
        public JsonElement serialize(Billing src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject billingObject = new JsonObject();
            billingObject.addProperty("address_1", src.getAddress_1());
            billingObject.addProperty("address_2", src.getAddress_2());
            billingObject.addProperty("city", src.getCity());
            billingObject.addProperty("company", src.getCompany());
            billingObject.addProperty("country", src.getCountry());
            billingObject.addProperty("email", src.getEmail());
            billingObject.addProperty("first_name", src.getFirst_name());
            billingObject.addProperty("last_name", src.getLast_name());
            billingObject.addProperty("postcode", src.getPostcode());
            billingObject.addProperty("phone", src.getPhone());
            billingObject.addProperty("state", src.getState());

            Log.e("postCustomer", "billing serialized");

            return billingObject;
        }
    }

    class GetShippingSerializer implements JsonSerializer<Shipping> {
        @Override
        public JsonElement serialize(Shipping src, Type typeOfSrc, JsonSerializationContext context) {
            Log.e("postCustomer", "shipping serialized");

            JsonObject shippingObject = new JsonObject();
            shippingObject.addProperty("address_1", src.getAddress_1());
            shippingObject.addProperty("address_2", src.getAddress_2());
            shippingObject.addProperty("city", src.getCity());
            shippingObject.addProperty("company", src.getCompany());
            shippingObject.addProperty("country", src.getCountry());
            shippingObject.addProperty("first_name", src.getFirst_name());
            shippingObject.addProperty("last_name", src.getLast_name());
            shippingObject.addProperty("postcode", src.getPostcode());
            shippingObject.addProperty("state", src.getState());

            return shippingObject;
        }
    }
}
