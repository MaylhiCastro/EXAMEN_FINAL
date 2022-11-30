package com.example.examen_final.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movimientos")
public class Movimientos {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "tipo")
    public String tipo;
    @ColumnInfo(name = "monto")
    public int monto;
    @ColumnInfo(name = "motivo")
    public String motivo;
    @ColumnInfo(name = "imagen")
    public String imagen;
    @ColumnInfo(name = "latitud")
    public double latitud;
    @ColumnInfo(name = "longitud")
    public double longitud;
}
