package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "RESULTS", primaryKeys = {"game_id", "quest_id", "ans_id"})
public class Result {

    @NonNull
    private int game_id;
    @NonNull
    private int quest_id;
    @NonNull
    private int ans_id;

    /* - Getters & Setters - */
    public int getGame_id() {return game_id;}
    public void setGame_id(int game_id) {this.game_id = game_id;}

    public int getQuest_id() {return quest_id;}
    public void setQuest_id(int quest_id) {this.quest_id = quest_id;}

    public int getAns_id() {return ans_id;}
    public void setAns_id(int ans_id) {this.ans_id = ans_id;}
}
