package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.Review;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetReviewDeserializer implements JsonDeserializer<List<Review>> {

    @Override
    public List<Review> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        List<Review> reviews = new ArrayList<>();

        JsonArray jsonArray = json.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            String reviewer = jsonObject.get("reviewer").getAsString();
            String review = jsonObject.get("review").getAsString();
            int rating = jsonObject.get("rating").getAsInt();
            String date = jsonObject.get("date_created").getAsString();

            reviews.add(new Review(reviewer, rating, review, date));
        }

        return reviews;
    }
}
