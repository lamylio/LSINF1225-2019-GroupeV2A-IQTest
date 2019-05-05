package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Game;

@Dao
public interface GameDao {

    @Query("SELECT * FROM GAMES WHERE game_id LIKE :game_id")
    Game findByID(int game_id);

    @Query("SELECT * FROM GAMES WHERE username LIKE :username")
    Game[] findByPlayer(String username);

    @Query("SELECT * FROM GAMES WHERE username LIKE :username ORDER BY game_id DESC LIMIT 1")
    Game findLastByPlayer(String username);

    @Insert
    void createGame(Game game);


}

