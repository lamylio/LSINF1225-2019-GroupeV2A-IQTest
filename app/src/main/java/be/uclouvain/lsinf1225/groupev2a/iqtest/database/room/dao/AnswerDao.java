package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao;

import androidx.room.Dao;
import androidx.room.Query;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Answer;

@Dao
public interface AnswerDao {

    @Query("SELECT * FROM ANSWERS WHERE quest_id LIKE :quest_id ORDER BY RANDOM()")
    Answer[] getAnswersFromQuestion(int quest_id);

}

