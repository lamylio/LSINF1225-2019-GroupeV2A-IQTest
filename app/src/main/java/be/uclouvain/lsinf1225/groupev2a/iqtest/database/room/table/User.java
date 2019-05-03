package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity(tableName = "USERS", primaryKeys = {"username"})
public class User {

    @NonNull
    private String username;
    @NonNull
    private String password;
    private boolean gender;
    private String city;
    private int age;

    public static User loggedUser;

    @Ignore
    public User(String username, String password) {
        setUsername(username);
        setPassword(hashPassword(password));
    }

    public User(){}

    /* - Useful functions - */

    public static String hashPassword(String password) {

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            return new String(digest.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            Log.e("IQW/DB/User", e.getMessage());
        }
        return password;
    }

    /* - Getters & Setters - */
    @NonNull
    public String getUsername() {
        return username;
    }
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }
    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    
}
