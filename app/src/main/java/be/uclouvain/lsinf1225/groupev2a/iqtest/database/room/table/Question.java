package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "QUESTIONS", primaryKeys = {"quest_id"})
public class Question {

    @NonNull
    private int quest_id;
    @NonNull
    private String statement;
    @NonNull
    private String type;
    private int weight = 1;

    /* - Getters & Setters - */
    public int getQuest_id() {return quest_id;}
    public void setQuest_id(int quest_id) {this.quest_id = quest_id;}

    @NonNull
    public String getStatement() {return statement;}
    public void setStatement(@NonNull String statement) {this.statement = statement;}

    @NonNull
    public String getType() {return type;}
    public void setType(@NonNull String type) {this.type = type;}

    public int getWeight() {return weight;}
    public void setWeight(int weight) {this.weight = weight;}
}
