package com.example.onlineshop.data.remote.retrofit;

import android.util.Log;

import com.example.onlineshop.data.model.ProductItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetSingleProductItemDeserializer implements JsonDeserializer<ProductItem> {

    public static final String TAG = "SingleProductItemDeserializer";

    @Override
    public ProductItem deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String productName = jsonObject.get("name").getAsString();
        String productPrice = jsonObject.get("price").getAsString();
        String url = jsonObject.get("permalink").getAsString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = format.parse(jsonObject.get("date_created").getAsString());
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        int rate = jsonObject.get("rating_count").getAsInt();

        String description = jsonObject.get("description").getAsString();

//        String html = description;
//        Document doc = Jsoup.parse(html);
//
//        doc.select("<br/>").remove();
//        doc.select("br").last().remove();
//        doc.select("<p/>").remove();
//        doc.select("p").last().remove();
//        description = doc.body().text();
        //        Document doc = Jsoup.parse(description, "", Parser.htmlParser());
//        Element imgs = doc.select("des");
//        imgs.remove();
//        String jsoupUrl = NetworkParams.BASE_URL
//                + "products/" + id + "?consumer_key="
//                + NetworkParams.CONSUMER_KEY +
//                "&consumer_secret=" + NetworkParams.CONSUMER_SECRET;
//        try {
//            Document document = Jsoup.connect(jsoupUrl).get();
//            description = document.ownText();
////            description = document.select("description").first().absUrl("description");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        JsonArray photoArray = jsonObject.get("images").getAsJsonArray();
        List<String> photoUrl = new ArrayList<>();
        for (int j = 0; j < photoArray.size(); j++) {
            JsonObject photoJsonObject = photoArray.get(j).getAsJsonObject();
            photoUrl.add(photoJsonObject.get("src").getAsString());
        }

        JsonArray categoryIdArray = jsonObject.get("categories").getAsJsonArray();
        List<Integer> categoriesId = new ArrayList<>();
        for (int j = 0; j < categoryIdArray.size(); j++) {
            JsonObject idJsonObject = categoryIdArray.get(j).getAsJsonObject();
            categoriesId.add(idJsonObject.get("id").getAsInt());
        }

        JsonArray relatedIdArray = jsonObject.get("related_ids").getAsJsonArray();
        List<Integer> relatedIds = new ArrayList<>();
        for (int j = 0; j < relatedIdArray.size(); j++) {
            relatedIds.add(relatedIdArray.get(j).getAsInt());
        }

        return new ProductItem(id, productName, productPrice, url, date, rate,
                description, photoUrl, categoriesId, relatedIds);
    }
}
