package be.uclouvain.lsinf1225.groupev2a.iqtest.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.dao.AnswerDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.dao.GameDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.dao.QuestDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.dao.ResultDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.dao.UserDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.table.Answer;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.table.Game;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.table.Question;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.table.Result;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.table.User;

/* Do not forget to insert the class in entities{} right below
 * And please don't increase the version, if you're getting an error, just clear the app's storage on the emulator (settings) */
@Database(version = 2, entities = {User.class, Game.class, Question.class, Answer.class, Result.class}, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String NAME = "iqwhizzdb";
    public static AppDatabase INSTANCE;

    /* Debug purposes  */
    private static RoomDatabase.Callback DB_CALLBACK =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    Utils.sendLog(this.getClass(), NAME + " :: onOpen");
                }
            };

    public static void setup(Context context) {
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, NAME).addCallback(DB_CALLBACK)
        /* Warning ! All DB which version ain't > 1 will be destroyed. .fallbackToDestructiveMigrationFrom(1) */.build();
    }

    abstract public UserDao userDao();
    abstract public GameDao gameDao();
    abstract public QuestDao questDao();
    abstract public AnswerDao answerDao();
    abstract public ResultDao resultDao();
}
