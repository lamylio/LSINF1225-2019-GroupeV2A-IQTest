package be.uclouvain.lsinf1225.groupev2a.iqtest.Database.room.table;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "FRIENDS", primaryKeys = {"username"})
public class Friend {
    @NonNull
    private String username;

}
