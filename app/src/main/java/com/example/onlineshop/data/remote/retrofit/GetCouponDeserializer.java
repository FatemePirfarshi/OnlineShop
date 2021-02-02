package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.CouponLines;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class GetCouponDeserializer implements JsonDeserializer<CouponLines> {
    @Override
    public CouponLines deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        String code = jsonObject.get("code").getAsString();
        String amount = jsonObject.get("amount").getAsString();

        return new CouponLines(code, amount);
    }

}
