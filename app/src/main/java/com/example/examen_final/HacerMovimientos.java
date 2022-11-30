package com.example.examen_final;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examen_final.entities.Cuentas;
import com.example.examen_final.entities.ImagenBase64;
import com.example.examen_final.entities.ImagenMovimientos;
import com.example.examen_final.entities.Movimientos;
import com.example.examen_final.services.CuentaServices;
import com.example.examen_final.services.ImagenService;
import com.example.examen_final.services.MovimientoServices;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HacerMovimientos extends AppCompatActivity {
    private Spinner spTipo;
    public ImageView imMovimiento;
    public EditText edMonto, edMotivo;
    public EditText etLatitud, etLongitud;
    String link;
    double latitud, longitud;
    String encoded;
    Cuentas cuentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_movimientos);

        spTipo = findViewById(R.id.spTipo);
        imMovimiento = findViewById(R.id.imMovimiento);
        edMonto = findViewById(R.id.edMonto);
        edMotivo = findViewById(R.id.edMotivo);
        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.combo_tipos, android.R.layout.simple_spinner_dropdown_item);

        spTipo.setAdapter(adapter);

        Intent intent = getIntent();
        String cuentaJson = intent.getStringExtra("CUENTA");

        cuentas = new Gson().fromJson(cuentaJson, Cuentas.class);

    }
    public void tomarFoto(View view){
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            abrirCamara();
        }
        else{
            requestPermissions(new String[] {Manifest.permission.CAMERA}, 100);//un número cualquiera
        }
    }

    public void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//con esto es lo mínimo necesario para abrir la cámara
        startActivityForResult(intent, 1000);//se le pone cualquier número, sirve como código de respeusta

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == RESULT_OK){// el CAMERA_REQUEST es para validar que sea una petición de abrir la cámara y el RESULT_OK es para validar que al abrir la cámara todo salio bien y no hubo errores
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imMovimiento.setImageBitmap(imageBitmap);

            //esto sirve para convertir bitmap a base64
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.i("MAIN_APP", encoded);
            ObtenerBase64();

        }
    }

    public void ObtenerBase64() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.imgur.com/")// -> Aquí va la URL sin el Path
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ImagenBase64 imagen = new ImagenBase64();
        imagen.image = encoded;

        ImagenService services = retrofit.create(ImagenService.class);
        services.create(imagen).enqueue(new Callback<ImagenMovimientos>() {
            @Override
            public void onResponse(Call<ImagenMovimientos> call, Response<ImagenMovimientos> response) {
                Log.i("MAIN_APP", String.valueOf(response.code()));
                ImagenMovimientos data = response.body();
                link = data.data.link;
                Log.i("MAIN_APP", new Gson().toJson(data));
            }

            @Override
            public void onFailure(Call<ImagenMovimientos> call, Throwable t) {

            }
        });
    }

    public void GuardarTransaccion(View view){
        Movimientos movimientos = new Movimientos();
        movimientos.cuentaId = cuentas.id;
        movimientos.tipo = spTipo.getSelectedItem().toString();
        movimientos.monto = Double.valueOf(edMonto.getText().toString());
        movimientos.motivo = edMotivo.getText().toString();
        movimientos.imagen = link;
        movimientos.latitud = latitud;
        movimientos.longitud = longitud;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://63590e60ff3d7bddb997b784.mockapi.io/")// -> Aquí va la URL sin el Path
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovimientoServices services = retrofit.create(MovimientoServices.class);
        services.create(cuentas.id, movimientos).enqueue(new Callback<Movimientos>() {
            @Override
            public void onResponse(Call<Movimientos> call, Response<Movimientos> response) {
                Log.i("MAIN_APP", String.valueOf(response.code()));
                Log.i("MAIN_APP", new Gson().toJson(response.body()).toString());
                Toast.makeText(getApplicationContext(), "Información guardada", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), DetalleCuenta.class);
                intent.putExtra("DATA_CUENTA", new Gson().toJson(cuentas).toString());//pasa datos
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Movimientos> call, Throwable t) {
                Log.i("MAIN_APP", "No se guardo los datos");
                Toast.makeText(getApplicationContext(), "No se guardó", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerUbicacion(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }

    public class Localizacion implements LocationListener {

        HacerMovimientos hacerMovimientos;
        public HacerMovimientos getMainActivity() {
            return hacerMovimientos;
        }

        public void setMainActivity(HacerMovimientos mainActivity) {
            this.hacerMovimientos = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            latitud = loc.getLatitude();
            longitud = loc.getLongitude();
            etLatitud.setText("Latitud: " + latitud);
            etLongitud.setText("Longitud: " + longitud);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            Toast.makeText(getApplicationContext(), "GPS Desactivado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            Toast.makeText(getApplicationContext(), "GPS Activado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
