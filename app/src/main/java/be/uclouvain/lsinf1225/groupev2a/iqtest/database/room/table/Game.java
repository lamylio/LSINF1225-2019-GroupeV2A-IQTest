package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

@Entity(tableName = "GAMES")
public class Game {

    @PrimaryKey(autoGenerate = true) @NonNull
    private int game_id;
    @NonNull
    private String username;
    @NonNull
    private String type;

    private String start_time;
    private String end_time;

    private String challenger;


    public Game(String username, String type) {
        setUsername(username);
        setType(type);
        setStart_time(Utils.dateFormated());
    }

    @NonNull
    public String getUsername() {
        return username;
    }
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public int getGame_id() {return game_id;}
    public void setGame_id(int game_id) {this.game_id = game_id;}

    @NonNull
    public String getType() {return type;}
    public void setType(@NonNull String type) {this.type = type;}

    public String getChallenger() {return challenger;}
    public void setChallenger(String challenger) {this.challenger = challenger;}

    public String getStart_time() {return start_time;}
    public void setStart_time(String start_time) {this.start_time = start_time;}

    public String getEnd_time() {return end_time;}
    public void setEnd_time(String end_time) {this.end_time = end_time;}
}
