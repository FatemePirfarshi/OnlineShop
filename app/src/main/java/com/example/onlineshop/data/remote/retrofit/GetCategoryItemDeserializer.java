package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.CategoryItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetCategoryItemDeserializer implements JsonDeserializer<List<CategoryItem>> {

    @Override
    public List<CategoryItem> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        List<CategoryItem> items = new ArrayList<>();

        JsonArray jsonArray = json.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            int id = jsonObject.get("id").getAsInt();
            String categoryName = jsonObject.get("name").getAsString();
            int count = jsonObject.get("count").getAsInt();

            JsonObject photoJsonObject = jsonObject.get("image").getAsJsonObject();
            String photoUrl = photoJsonObject.get("src").getAsString();

            items.add(new CategoryItem(id, categoryName, count, photoUrl));
        }
        return items;
    }
}
