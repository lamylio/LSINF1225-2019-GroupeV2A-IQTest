package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Hashtable;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.DatabaseHelper;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Answer;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Game;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Question;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Result;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

public class GameActivity extends AppCompatActivity {

    public static Game game;
    public static Hashtable<Question, Answer[]> answersTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemode);
    }

    /*-------MODE-----------------------------------------*/

    public void onModeClick(View view){
        /* We only use one eventHandler to handle all the button.
           Send to the ModeActivity and add an extraFlag with the ID of the clicked one */
        setContentView(R.layout.activity_modeinfos);
        updateUI(view);
        answersTable = new Hashtable<>();
    }

    int mode_id;
    String mode;
    private void updateUI(View view){
        mode_id = view.getId();
        TextView mode_title = findViewById(R.id.mode_title);
        TextView mode_description = findViewById(R.id.mode_description);
        TextView mode_time = findViewById(R.id.mode_time);

        switch (mode_id) {
            case R.id.choose_friendButton:
                mode = "multiplayers";
                mode_title.setText(getResources().getText(R.string.mode_multiplayer).toString());
                mode_description.setText(getResources().getText(R.string.mode_multiplayer_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_normalButton:
                mode = "normal";
                mode_title.setText(getResources().getText(R.string.mode_normal).toString());
                mode_description.setText(getResources().getText(R.string.mode_normal_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_40).toString());
                break;
            case R.id.choose_speedButton:
                mode = "speed";
                mode_title.setText(getResources().getText(R.string.mode_speed).toString());
                mode_description.setText(getResources().getText(R.string.mode_speed_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_logiqueButton:
                mode = "logique";
                mode_title.setText(getResources().getText(R.string.mode_logique).toString());
                mode_description.setText(getResources().getText(R.string.mode_logique_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_numeriqueButton:
                mode = "numerique";
                mode_title.setText(getResources().getText(R.string.mode_numerique).toString());
                mode_description.setText(getResources().getText(R.string.mode_numerique_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_spatialButton:
                mode = "spacial";
                mode_title.setText(getResources().getText(R.string.mode_spatial).toString());
                mode_description.setText(getResources().getText(R.string.mode_spatial_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_verbalButton:
                mode = "verbal";
                mode_title.setText(getResources().getText(R.string.mode_verbal).toString());
                mode_description.setText(getResources().getText(R.string.mode_verbal_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            default:
                Utils.sendLog(this.getClass(), "Unknown mode : " + mode_id);
                break;
        }
    }

    public void onCategoriesClick(View v){setContentView(R.layout.activity_category);}

    /*-------------GAME--------------------------------*/

    public void constructGame(View view){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                /* Sorry Jérôme, j'ai modifié tout ce que tu as fait xD
                   J'suis passé par des string pour le switch mais on peut tout à faire remettre les id, ça change rien
                 */
                int limit = 5;
                Question[] questTab = new Question[0];

                DatabaseHelper.INSTANCE.gameDao().createGame(new Game(0, User.loggedUser.getUsername(), mode));
                game = DatabaseHelper.INSTANCE.gameDao().findLastByPlayer(User.loggedUser.getUsername());

                switch (mode) {
                    case "normal":
                        limit = 40;
                    case "speed":
                    case "multiplayers":
                        questTab = DatabaseHelper.INSTANCE.questDao().randomQuestions(limit);
                        break;
                    case "logique":
                    case "numerique":
                    case "spacial":
                    case "verbal":
                        questTab = DatabaseHelper.INSTANCE.questDao().randomTypeQuestions(mode, limit);
                        break;
                }

                if (questTab.length != limit)
                    throw new Error("ERROR : PAS ASSEZ DE QUESTIONS - Il n'y a pas encore la répétition en boucle");

                for (Question question : questTab) {
                    DatabaseHelper.INSTANCE.resultDao().createResult(new Result(game.getGame_id(), question.getQuest_id()));
                    Answer[] answers = DatabaseHelper.INSTANCE.answerDao().getAnswersFromQuestion(question.getQuest_id());

                    answersTable.put(question, answers);
                }
                Utils.changeActivity(getApplicationContext(),QuestionActivity.class);
            }
        });
    }
    /*-------------DIVERS------------------------------*/
    @Override
    public void onBackPressed() {
        View v = findViewById(R.id.activity_choosemode);
        /* Don't ask me why its unequals but its working lol */
        if(getCurrentFocus() != v) {
            Utils.changeActivity(getApplicationContext(), ProfileActivity.class);
            finish();
        }else{
            setContentView(R.layout.activity_choosemode);
        }
    }
}
