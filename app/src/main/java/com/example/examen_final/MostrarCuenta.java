package com.example.examen_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.examen_final.adapters.CuentasAdapter;
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

public class MostrarCuenta extends AppCompatActivity {
    RecyclerView rvCuentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cuenta);

        rvCuentas = findViewById(R.id.rvCuentas);

        AppDatabase db = AppDatabase.getInstance(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://63590e60ff3d7bddb997b784.mockapi.io/")// -> Aquí va la URL sin el Path
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CuentaServices services = retrofit.create(CuentaServices.class);
        services.get().enqueue(new Callback<List<Cuentas>>() {
            @Override
            public void onResponse(Call<List<Cuentas>> call, Response<List<Cuentas>> response) {
                List<Cuentas> data = response.body();

                /*for(int i = 0;i<data.size();i++){
                    Cuentas cuentasAux = data.get(i);
                    if(cuentasAux != null){
                        db.cuentasDao().update(cuentasAux);
                    }
                    else{
                        db.cuentasDao().create(cuentasAux);
                    }
                }

                List<Cuentas> cuentas = db.cuentasDao().getAll();
                Log.i("MAIN_APP", new Gson().toJson(cuentas));*/

                rvCuentas = findViewById(R.id.rvCuentas);
                rvCuentas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvCuentas.setAdapter(new CuentasAdapter(data));

                Log.i("MAIN_APP", "Response: "+response.body().size());
                Log.i("MAIN_APP", "Response: "+response.code());
            }

            @Override
            public void onFailure(Call<List<Cuentas>> call, Throwable t) {
                Log.i("MAIN_APP", "Fallo");
            }
        });
    }
}