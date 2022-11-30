package com.example.examen_final.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.examen_final.dao.CuentasDao;
import com.example.examen_final.dao.MovimientosDao;
import com.example.examen_final.entities.Cuentas;
import com.example.examen_final.entities.Movimientos;

@Database(entities = {Cuentas.class, Movimientos.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CuentasDao cuentasDao();
    public abstract MovimientosDao movimientosDao();

    public static AppDatabase getInstance(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "examen24-11-2022")
                .allowMainThreadQueries()
                .build();
    }

}
