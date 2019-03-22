package be.llamy.iqwhizz.Database.Dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import be.llamy.iqwhizz.Database.Table.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM USERS WHERE username LIKE :username")
    User findByName(String username);

    @Query("SELECT * FROM USERS WHERE 1")
    List<User> getAllUsers();

    @Insert
    public void registerUser(User user);

}

