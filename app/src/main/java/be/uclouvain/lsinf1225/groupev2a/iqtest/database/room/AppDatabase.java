package be.uclouvain.lsinf1225.groupev2a.iqtest.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao.AnswerDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao.FriendDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao.GameDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao.QuestDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao.ResultDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.dao.UserDao;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Answer;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Friend;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Game;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Question;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Result;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

/* Do not forget to insert the class in entities{} right below
 * If you're getting an error, just clear the app's storage on the emulator (settings) */
@Database(exportSchema = false, version = DatabaseHelper.VERSION,
        entities = {
            User.class,
            Game.class,
            Question.class,
            Answer.class,
            Result.class,
            Friend.class
        })
public abstract class AppDatabase extends RoomDatabase {

    abstract public UserDao userDao();
    abstract public GameDao gameDao();
    abstract public QuestDao questDao();
    abstract public AnswerDao answerDao();
    abstract public ResultDao resultDao();
    abstract public FriendDao friendDao();
}
