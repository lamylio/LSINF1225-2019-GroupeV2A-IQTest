package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Result;

@Dao
public interface ResultDao {

    @Query("SELECT * FROM RESULTS WHERE game_id LIKE :game_id")
    Result[] getAllResultsFromGame(int game_id);

    @Query("SELECT * FROM RESULTS WHERE game_id = :game_id AND ans_id = 0")
    Result[] getUnrespondedResultsFromGame(int game_id);

    @Insert
    void createResult(Result result);

    @Query("UPDATE RESULTS SET ans_id = :ans_id WHERE game_id = :game_id AND quest_id = :quest_id")
    void updateResult(int game_id, int quest_id, int ans_id);

}
