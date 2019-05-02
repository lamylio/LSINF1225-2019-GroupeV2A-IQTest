package be.uclouvain.lsinf1225.groupev2a.iqtest.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.table.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM USERS WHERE username LIKE :username")
    User findByName(String username);

    @Query("SELECT * FROM USERS WHERE 1")
    List<User> getAllUsers();

    @Insert
    void registerUser(User user);

}

