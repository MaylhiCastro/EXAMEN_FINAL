package com.example.examen_final.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cuentas")
public class Cuentas {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "nombre")
    public String nombre;
    @ColumnInfo(name = "saldo")
    public int saldo;
}
