package be.uclouvain.lsinf1225.groupev2a.iqtest.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Database.Dao.UserDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Database.Table.User;

@Database(version = 1, entities = {User.class}, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String NAME = "iqwhizzdb";
    public static AppDatabase INSTANCE;
    /**
     * Debug purposes
     **/
    private static RoomDatabase.Callback DB_CALLBACK =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    Log.i("IQW/AppDatabase", NAME + " :: onOpen");
                }
            };

    public static void setup(Context context) {
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, NAME).addCallback(DB_CALLBACK).build();
    }

    abstract public UserDao userDao();
}
