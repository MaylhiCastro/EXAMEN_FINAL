package com.example.examen_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegistrarMovimiento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_movimiento);

        etTitulo = findViewById(R.id.etTitulo);
        etSinopsis = findViewById(R.id.etSinopsis);
        ivPhoto = findViewById(R.id.ivPhoto);
    }
}