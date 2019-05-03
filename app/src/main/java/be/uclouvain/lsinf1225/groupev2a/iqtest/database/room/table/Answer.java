package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "ANSWERS", primaryKeys = {"ans_id"})
public class Answer {

    @NonNull
    private int ans_id;
    @NonNull
    private int quest_id;
    @NonNull
    private String answer;
    @NonNull
    private boolean isCorrect;

    /* - Getters & Setters - */

    public int getAns_id() {return ans_id;}
    public void setAns_id(int ans_id) {this.ans_id = ans_id;}

    public int getQuest_id() {return quest_id;}
    public void setQuest_id(int quest_id) {this.quest_id = quest_id;}

    public String getAnswer() {return answer;}
    public void setAnswer(String answer) {this.answer = answer;}

    public boolean isCorrect() {return isCorrect;}
    public void setCorrect(boolean correct) {isCorrect = correct;}
}
