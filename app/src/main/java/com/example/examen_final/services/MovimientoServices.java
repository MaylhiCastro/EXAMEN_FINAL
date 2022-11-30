package com.example.examen_final.services;

import com.example.examen_final.entities.Movimientos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MovimientoServices {
    @GET("cuentas/:id/movimientos")
    Call<Movimientos> finById(@Path("movimientosId") int id);

    @GET("cuentas/:id/movimientos")
    Call<List<Movimientos>> get();

    @POST("cuentas/:id/movimientos")
    Call<Movimientos> create(@Body Movimientos movimientos);

    @PUT("cuentas/:id/movimientos/:id")
    Call<Movimientos> update(@Path("id") int id, @Body Movimientos movimientos);

    @DELETE("cuentas/:id/movimientos/:id")
    Call<Movimientos> delete(@Path("id") int id);
}
