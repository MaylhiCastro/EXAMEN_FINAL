package com.example.examen_final.services;

import com.example.examen_final.entities.ImagenBase64;
import com.example.examen_final.entities.ImagenMovimientos;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ImagenService {
    @Headers("Authorization: Client-ID 8bcc638875f89d9")
    @POST("3/image")
    Call<ImagenMovimientos> create(@Body ImagenBase64 image);
}
