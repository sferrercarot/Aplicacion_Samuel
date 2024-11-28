package com.example.aplicacion_samuel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.io.Serializable;
import java.util.List;

@Dao
public interface JugadorDAO{
    @Query("select * from Jugadores_futbol")
    LiveData<List<Jugador>> getJugadores();

    @Insert
    void addJugador(Jugador jugador);

    @Insert
    static void addJugadores(List<Jugador> jugadores) {}

    @Delete
    void deleteJugador(Jugador jugador);

    @Query("DELETE FROM Jugadores_futbol")
    static void deleteJugador() {}

}
