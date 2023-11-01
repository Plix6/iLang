package com.example.ilang.api;

import okhttp3.*;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TranslationClient {
    private final OkHttpClient client = new OkHttpClient();

    public interface Callback {
        void onResponse(String response);

        void onFailure(String errorMessage);
    }

    public void translateText(String from, String to, ArrayList<String> words, final Callback callback) {
        MediaType mediaType = MediaType.parse("application/json");
        JSONArray jsonArray = new JSONArray(words);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("from", from);
            jsonBody.put("to", to);
            jsonBody.put("e", ""); // Include an empty string for the "e" parameter
            jsonBody.put("q", jsonArray);
        } catch (JSONException e) {
            callback.onFailure("Error creating JSON request");
            return;
        }

        RequestBody body = RequestBody.create(mediaType, jsonBody.toString());

        Request request = new Request.Builder()
                .url("https://rapid-translate-multi-traduction.p.rapidapi.com/t")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", "b6d66b24f6msh894d87e0f3b55a8p1932bfjsn48067d837d94")
                .addHeader("X-RapidAPI-Host", "rapid-translate-multi-traduction.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    callback.onResponse(responseBody);
                } else {
                    callback.onFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Request failed");
            }
        });
    }
}

