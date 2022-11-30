package com.example.examen_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.examen_final.adapters.MovimientosAdapter;
import com.example.examen_final.entities.Cuentas;
import com.example.examen_final.entities.Movimientos;
import com.example.examen_final.services.MovimientoServices;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostrarMovimientos extends AppCompatActivity {

    Cuentas cuentas;
    RecyclerView rvMovimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_movimientos);

        rvMovimientos = findViewById(R.id.rvMovimientos);

        Intent intent = getIntent();
        String cuentaJson = intent.getStringExtra("CUENTA");

        cuentas = new Gson().fromJson(cuentaJson, Cuentas.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://63590e60ff3d7bddb997b784.mockapi.io/")// -> Aquí va la URL sin el Path
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovimientoServices services = retrofit.create(MovimientoServices.class);
        services.get(cuentas.id).enqueue(new Callback<List<Movimientos>>() {
            @Override
            public void onResponse(Call<List<Movimientos>> call, Response<List<Movimientos>> response) {
                List<Movimientos> movimientos = response.body();
                Log.i("MAIN_APP", new Gson().toJson(movimientos).toString());
                rvMovimientos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvMovimientos.setAdapter(new MovimientosAdapter(movimientos));
                Log.i("MAIN_APP", "Se cargo los movimientos");
            }

            @Override
            public void onFailure(Call<List<Movimientos>> call, Throwable t) {
                Log.i("MAIN_APP", "No se cargo los movimientos");
            }
        });
    }
}