package com.example.examen_final.services;

import com.example.examen_final.entities.Cuentas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CuentaServices {
    @GET("cuentas")
    Call<Cuentas> finById(@Path("cuentasId")int id);
    @GET("cuentas")
    Call<List<Cuentas>> get();

    @POST("cuentas")
    Call<Cuentas> create(@Body Cuentas cuentas);

    @PUT("cuentas/{id}")
    Call<Cuentas> update(@Path("id") int id, @Body Cuentas cuentas);

    @DELETE("cuentas/{id}")
    Call<Cuentas> delete(@Path("id") int id);
}
