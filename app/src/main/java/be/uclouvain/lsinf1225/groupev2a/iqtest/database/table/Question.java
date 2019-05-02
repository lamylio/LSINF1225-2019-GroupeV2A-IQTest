package be.uclouvain.lsinf1225.groupev2a.iqtest.database.table;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "QUESTIONS", primaryKeys = {"id"})
public class Question {

    @NonNull
    private int id;
    @NonNull
    private String statement;
    @NonNull
    private String type;
    private int weigth = 1;

    /* - Getters & Setters - */
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    @NonNull
    public String getStatement() {return statement;}
    public void setStatement(@NonNull String statement) {this.statement = statement;}

    @NonNull
    public String getType() {return type;}
    public void setType(@NonNull String type) {this.type = type;}

    public int getWeigth() {return weigth;}
    public void setWeigth(int weigth) {this.weigth = weigth;}
}
