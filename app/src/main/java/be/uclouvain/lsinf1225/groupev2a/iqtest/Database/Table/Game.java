package be.uclouvain.lsinf1225.groupev2a.iqtest.Database.Table;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity(tableName = "GAMES", primaryKeys = {"id"})
public class Game {

    @NonNull @PrimaryKey
    private int id;
    @NonNull
    private String username;
    @NonNull
    private String type;

    private Time start_time;
    private Time end_time;

    private String challenger;

    public Game(){}

    @NonNull
    public String getUsername() {
        return username;
    }
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    @NonNull
    public String getType() {return type;}
    public void setType(@NonNull String type) {this.type = type;}

    public Time getStart_time() {return start_time;}
    public void setStart_time(Time start_time) {this.start_time = start_time;}

    public Time getEnd_time() {return end_time;}
    public void setEnd_time(Time end_time) {this.end_time = end_time;}

    public String getChallenger() {return challenger;}
    public void setChallenger(String challenger) {this.challenger = challenger;}
}
