package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.Customer;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetCustomerDeserializer implements JsonDeserializer<List<Customer>> {

    @Override
    public List<Customer> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        List<Customer> items = new ArrayList<>();

        JsonArray jsonArray = json.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            String email = jsonObject.get("email").getAsString();
            String userName = jsonObject.get("username").getAsString();

            items.add(new Customer(email, userName));
        }
        return items;
    }
}
