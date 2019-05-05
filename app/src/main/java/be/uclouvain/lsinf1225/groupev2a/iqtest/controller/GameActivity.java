package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.DatabaseHelper;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Game;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Question;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Result;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

public class GameActivity extends AppCompatActivity {

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
    }

    int mode_id;
    private void updateUI(View view){
        mode_id = view.getId();
        TextView mode_title = findViewById(R.id.mode_title);
        TextView mode_description = findViewById(R.id.mode_description);
        TextView mode_time = findViewById(R.id.mode_time);

        switch (mode_id) {
            case R.id.choose_friendButton:
                mode_title.setText(getResources().getText(R.string.mode_multiplayer).toString());
                mode_description.setText(getResources().getText(R.string.mode_multiplayer_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_normalButton:
                mode_title.setText(getResources().getText(R.string.mode_normal).toString());
                mode_description.setText(getResources().getText(R.string.mode_normal_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_40).toString());
                break;
            case R.id.choose_speedButton:
                mode_title.setText(getResources().getText(R.string.mode_speed).toString());
                mode_description.setText(getResources().getText(R.string.mode_speed_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_logiqueButton:
                mode_title.setText(getResources().getText(R.string.mode_logique).toString());
                mode_description.setText(getResources().getText(R.string.mode_logique_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_numeriqueButton:
                mode_title.setText(getResources().getText(R.string.mode_numerique).toString());
                mode_description.setText(getResources().getText(R.string.mode_numerique_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_spatialButton:
                mode_title.setText(getResources().getText(R.string.mode_spatial).toString());
                mode_description.setText(getResources().getText(R.string.mode_spatial_description).toString());
                mode_time.setText(getResources().getText(R.string.mode_time_5).toString());
                break;
            case R.id.choose_verbalButton:
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
        /* On a pas besoin d'un game_id puisqu'il est autogenerate */
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                switch (mode_id){
                    case R.id.choose_speedButton:

                        /* En réalité, il faudrait mettre new_game et questTab en static dans la classe elle-même
                           pour pouvoir y accéder depuis QuestionActivity ^^

                           Je suis sûr qu'il y a moyen de faire ça un peu plus proprement qu'avec un switch dans updateUI et un dans constructGame
                           mais je vous laisse gérer ça :*
                         */

                        DatabaseHelper.INSTANCE.gameDao().createGame(new Game(0, User.loggedUser.getUsername(), "speed"));
                        Game new_game = DatabaseHelper.INSTANCE.gameDao().findLastByPlayer(User.loggedUser.getUsername());

                        Question[] questTab = DatabaseHelper.INSTANCE.questDao().randomQuestions(5);
                        if(questTab.length != 5) break;

                        for (Question question : questTab) {
                            DatabaseHelper.INSTANCE.resultDao().createResult(new Result(new_game.getGame_id(), question.getQuest_id()));
                            //Utils.sendLog(this.getClass(), new_game.getGame_id() + " " + question.getQuest_id());
                        }

                        break;
                }
            }
        });

        Utils.changeActivity(getApplicationContext(),QuestionActivity.class);
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
