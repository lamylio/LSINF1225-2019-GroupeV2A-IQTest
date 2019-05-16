package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao;

import androidx.room.Dao;
import androidx.room.Query;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Answer;

@Dao
public interface AnswerDao {

    @Query("SELECT * FROM ANSWERS WHERE quest_id LIKE :quest_id ORDER BY RANDOM()")
    Answer[] getAnswersFromQuestion(int quest_id);

    @Query("SELECT A.quest_id, A.ans_id, A.answer, A.isCorrect " +
            "FROM ANSWERS A, RESULTS R " +
            "WHERE R.game_id = :game_id " +
            "AND A.quest_id = R.quest_id " +
            "AND R.ans_id = A.ans_id")
    Answer[] getAnswersFromGame(int game_id);

    @Query("SELECT COUNT(A.ans_id) " +
            "FROM ANSWERS A, RESULTS R " +
            "WHERE R.game_id = :game_id " +
            "AND A.quest_id = R.quest_id " +
            "AND A.ans_id = R.ans_id " +
            "AND A.isCorrect = 1")
    int howManyCorrectAnswersFromGame(int game_id);

}

