package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Friend;

@Dao
public interface FriendDao {

    @Insert
    void insertFriendship(Friend friendship);

    @Query("SELECT * FROM FRIENDS WHERE user1 LIKE :username")
    Friend findByUsername(String username);

}
