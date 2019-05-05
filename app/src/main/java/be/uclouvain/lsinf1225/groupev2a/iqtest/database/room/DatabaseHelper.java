package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

public class DatabaseHelper {

    public static AppDatabase INSTANCE;

    public static final String NAME = "lsinf.db";
    public static final int VERSION = 3;

    public DatabaseHelper(Context context, boolean destruct){
        if(destruct) this.setupAndDestruct(context);
        else this.setup(context);
    }

    /* Debug purposes  */
    private RoomDatabase.Callback DB_CALLBACK =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    Utils.sendLog(this.getClass(), NAME + " :: onOpen");
                }
            };

    private void setup(Context context) {
        copyExistingDatabase(context);
        INSTANCE = Room.databaseBuilder(context, AppDatabase.class, NAME).addCallback(DB_CALLBACK).build();
    }
    private void setupAndDestruct(Context context) {
        copyExistingDatabase(context);
        INSTANCE = Room.databaseBuilder(context, AppDatabase.class, NAME).addCallback(DB_CALLBACK).fallbackToDestructiveMigration().build();
    }

    private void copyExistingDatabase(Context context) {
        final File dbPath = context.getDatabasePath(DatabaseHelper.NAME);

        /* If the database already exists, return it */
        if (dbPath.exists()) {
            Utils.sendLog(this.getClass(), "Room database already exists, no copy");
            return;
        }
        /* Make sure we have a path to the file*/
        dbPath.getParentFile().mkdirs();
        /* Try to copy database file */
        try {
            final InputStream inputStream = context.getResources().getAssets().open(DatabaseHelper.NAME);
            final OutputStream output = new FileOutputStream(dbPath);
            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            inputStream.close();
            Utils.sendLog(this.getClass(), "Prepopulated databased successfully copied");
        }
        catch (IOException e) {
            Log.e("IQW/DBHelper", "Failed to open existing database");
            e.printStackTrace();
        }
    }

}
