package com.example.examen_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examen_final.database.AppDatabase;
import com.example.examen_final.entities.Cuentas;
import com.example.examen_final.services.CuentaServices;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleCuenta extends AppCompatActivity {

    TextView tvNombre;
    Cuentas cuentas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cuenta);
        tvNombre = findViewById(R.id.tvNombreCuentadetalle);

        Intent intent = getIntent();
        String cuentaJson = intent.getStringExtra("DATA_CUENTA");
        cuentas = new Gson().fromJson(cuentaJson, Cuentas.class);
        tvNombre.setText(cuentas.nombre);

    }
    public void Registrar(View view){
        Intent intent = new Intent(getApplicationContext(), HacerMovimientos.class);
        intent.putExtra("CUENTA", new Gson().toJson(cuentas));
        startActivity(intent);
    }

    public void VerMovimientos(View view){
        Intent intent = new Intent(getApplicationContext(), MostrarMovimientos.class);
        intent.putExtra("CUENTA", new Gson().toJson(cuentas));
        startActivity(intent);
    }
    public void Sincronizar(View view){



    }
}