package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM USERS WHERE username LIKE :username")
    User findByName(String username);

    @Query("SELECT * FROM USERS WHERE 1")
    List<User> getAllUsers();

    @Insert
    void registerUser(User user);

    @Query("UPDATE USERS SET password = :hashed_password WHERE username = :username")
    void updatePassword(String username, String hashed_password);
}

