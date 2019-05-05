package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Question;

@Dao
public interface QuestDao {

    @Query("SELECT * FROM QUESTIONS WHERE 1")
    List<Question> getAllQuestions();

    @Query("SELECT * FROM QUESTIONS WHERE type LIKE :type")
    Question randomQuestion(String type);

    @Query("SELECT * FROM QUESTIONS WHERE type LIKE :type ORDER BY RANDOM() LIMIT :limit")
    Question[] randomTypeQuestions(String type, int limit);

    @Query("SELECT * FROM QUESTIONS ORDER BY RANDOM() LIMIT :limit")
    Question[] randomQuestions(int limit);

    @Insert
    void createQuestion(Question question);

    @Insert
    void createQuestions(Question[] questions);
}
