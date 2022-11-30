package com.example.examen_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examen_final.entities.Cuentas;
import com.example.examen_final.services.CuentaServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearCuenta extends AppCompatActivity {
    EditText edNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        edNombre = findViewById(R.id.edNombreCuenta);
        //guardarCuenta();
    }

    public void guardarCuenta(View view) {
        Cuentas cuentas = new Cuentas();
        cuentas.nombre = edNombre.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://63590e60ff3d7bddb997b784.mockapi.io/")// -> Aquí va la URL sin el Path
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CuentaServices services = retrofit.create(CuentaServices.class);
        services.create(cuentas).enqueue(new Callback<Cuentas>() {
            @Override
            public void onResponse(Call<Cuentas> call, Response<Cuentas> response) {
                Log.i("MAIN_APP", "Response: " + response.code());
                Toast.makeText(getApplicationContext(), "Se creo la cuenta", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MostrarCuenta.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Cuentas> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se creo la cuenta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}