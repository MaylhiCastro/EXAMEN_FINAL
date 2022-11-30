package com.example.examen_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examen_final.entities.Movimientos;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetalleMovimientos extends AppCompatActivity {

    public Movimientos movimientos;
    TextView tvTipoDetalleMovimiento, tvMontoDetalleMovimiento;
    ImageView imImagenDetalleMovimiento;

    TextView tvTipodemovimiento, tvSaldoDetallemovimiento;
    ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_movimientos);

        tvTipodemovimiento = findViewById(R.id.tvTipodemovimiento);
        tvSaldoDetallemovimiento = findViewById(R.id.tvSaldoDetallemovimiento);
        ivPhoto = findViewById(R.id.ivPhoto);

        Intent intent = getIntent();
        String movimientoJson = intent.getStringExtra("MOVIMIENTO_DATA");

        Log.i("MAIN_APP", new Gson().toJson(movimientoJson));

        movimientos = new Gson().fromJson(movimientoJson, Movimientos.class);
        tvTipodemovimiento.setText(movimientos.tipo);
        tvSaldoDetallemovimiento.setText(""+movimientos.monto);
        Picasso.get().load(movimientos.imagen).into(ivPhoto);

    }

    public void verMapa(View view){
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("MOVIMIENTO_DATA", new Gson().toJson(movimientos));
        startActivity(intent);
    }
}