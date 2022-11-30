package com.example.examen_final.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.examen_final.entities.Cuentas;

import java.util.List;
@Dao
public interface CuentasDao {
    @Query("SELECT * FROM Cuentas")
    List<Cuentas> getAll();

    @Query("SELECT * FROM Cuentas where id = :id")
    Cuentas find(int id);

    @Insert
    void create(Cuentas cuentas);

    @Update
    void update(Cuentas cuentas);

    @Delete
    void delete(Cuentas cuentas);
}
