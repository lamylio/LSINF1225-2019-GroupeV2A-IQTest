package be.llamy.iqwhizz.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import be.llamy.iqwhizz.Database.Dao.UserDao;
import be.llamy.iqwhizz.Database.Table.UserTable;

@Database(version = 1, entities = {UserTable.class}, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    public static AppDatabase INSTANCE;
    public static final String NAME = "iqwhizzdb";

    abstract public UserDao userDao();

    public static void setup(Context context){
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, NAME).addCallback(DB_CALLBACK).build();
    }

    /** Debug purposes **/
    private static RoomDatabase.Callback DB_CALLBACK =
        new RoomDatabase.Callback(){
            @Override
            public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                Log.i("DB", NAME + " :: onOpen");
            }
        };
}
