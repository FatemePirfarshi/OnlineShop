package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.CouponLines;
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

public class GetCouponDeserializer implements JsonDeserializer<List<CouponLines>> {

    @Override
    public List<CouponLines> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        List<CouponLines> items = new ArrayList<>();

        JsonArray jsonArray = json.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            String code = jsonObject.get("code").getAsString();
            String amount = jsonObject.get("amount").getAsString();

            items.add(new CouponLines(code, amount));
        }
        return items;
    }
}
