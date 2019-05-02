package be.uclouvain.lsinf1225.groupev2a.iqtest.database.table;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "ANSWERS", primaryKeys = {"id"})
public class Answer {

    @NonNull
    private int id;
    @NonNull
    private int quest_id;
    @NonNull
    private String answer;
    @NonNull
    private boolean isCorrect;

    /* - Getters & Setters - */
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getQuest_id() {return quest_id;}
    public void setQuest_id(int quest_id) {this.quest_id = quest_id;}

    public String getAnswer() {return answer;}
    public void setAnswer(String answer) {this.answer = answer;}

    public boolean isCorrect() {return isCorrect;}
    public void setCorrect(boolean correct) {isCorrect = correct;}
}
