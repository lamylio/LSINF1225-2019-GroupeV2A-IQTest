package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Result;

@Dao
public interface ResultDao {

    @Insert
    void createResult(Result result);
}
