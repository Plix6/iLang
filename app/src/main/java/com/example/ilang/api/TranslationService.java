package com.example.ilang.api;

import com.example.ilang.pojo.TranslationRequest;
import com.example.ilang.pojo.TranslationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TranslationService {
    @POST("t")
    Call<TranslationResponse> translate(
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String apiHost,
            @Body TranslationRequest request
    );
}

