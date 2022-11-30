package com.example.examen_final.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.examen_final.entities.Movimientos;

import java.util.List;
@Dao
public interface MovimientosDao {
    @Query("SELECT * FROM Movimientos")
    List<Movimientos> getAll();

    @Query("SELECT * FROM Movimientos where id = :id")
    Movimientos find(int id);

    @Insert
    void create(Movimientos movimientos);

    @Update
    void update(Movimientos movimientos);

    @Delete
    void delete(Movimientos movimientos);
}
