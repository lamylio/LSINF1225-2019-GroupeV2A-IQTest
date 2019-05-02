package be.uclouvain.lsinf1225.groupev2a.iqtest.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.table.Game;

@Dao
public interface GameDao {

    @Query("SELECT * FROM GAMES WHERE id LIKE :game_id")
    Game findByID(int game_id);

}

