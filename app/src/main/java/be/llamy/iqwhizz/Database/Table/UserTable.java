package be.llamy.iqwhizz.Database.Table;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "USERS", primaryKeys = {"username"})
public class UserTable {

    @NonNull
    private String username;
    @NonNull
    private String password;

    private boolean gender;
    private String city;
    private int age;


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
