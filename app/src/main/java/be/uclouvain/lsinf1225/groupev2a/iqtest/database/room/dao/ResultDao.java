package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Result;

@Dao
public interface ResultDao {

    @Query("SELECT * FROM RESULTS WHERE game_id LIKE :game_id AND quest_id")
    Result[] getAllResultFromGame(int game_id);

    @Insert
    void createResult(Result result);

    @Update
    void updateResult(Result result);
}
