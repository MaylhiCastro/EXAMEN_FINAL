package com.example.examen_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Crear(View view){
        Intent intent = new Intent(getApplicationContext(), CrearCuenta.class);
        startActivity(intent);
    }
    public void Mostrar(View view){
        Intent intent = new Intent(getApplicationContext(), MostrarCuenta.class);
        startActivity(intent);
    }
}