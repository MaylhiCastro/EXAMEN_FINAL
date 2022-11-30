package com.example.examen_final.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cuentas")
public class Cuentas {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "nombre")
    public String nombre;
}
