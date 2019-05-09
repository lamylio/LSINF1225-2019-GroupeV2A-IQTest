package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "FRIENDS", primaryKeys = {"user1"})
public class Friend {

    @NonNull
    private String user1;
    @NonNull
    private String user2;
    @NonNull
    private int status;

    @Ignore
    public Friend(String user1, String user2){
        setUser1(user1);
        setUser2(user2);
        setStatus(0);
    }

    public Friend(){}

    @NonNull
    public String getUser1() {
        return user1;
    }

    public void setUser1(@NonNull String user1) {
        this.user1 = user1;
    }

    @NonNull
    public String getUser2() {
        return user2;
    }

    public void setUser2(@NonNull String user2) {
        this.user2 = user2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}