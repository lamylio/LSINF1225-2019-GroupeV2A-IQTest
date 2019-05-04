package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.DatabaseHelper;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Game;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Question;
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

    int id;
    private void updateUI(View view){
        String s_id = String.valueOf(view.getId());
        if(s_id == null) {
            Utils.changeActivity(getApplicationContext(), GameActivity.class);
            Utils.gimmeToast(getApplicationContext(), "Erreur - Impossible de retrouver le mode choisi ");
            finish();
            return;
        }
        id = Integer.parseInt(s_id);
        TextView mode_title = findViewById(R.id.mode_title);
        TextView mode_description = findViewById(R.id.mode_description);
        TextView mode_time = findViewById(R.id.mode_time);

        switch (id) {
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
                Utils.sendLog(this.getClass(), "Unknown mode : " + id);
                break;
        }
    }

    public void onCategoriesClick(View v){setContentView(R.layout.activity_category);}

    /*-------------GAME--------------------------------*/

    int game_id=0;
    public void constructGame(View view){
        game_id+=1;
        id = view.getId();
        switch (id){
            case R.id.choose_speedButton:
                Game new_game = new Game(game_id,User.loggedUser.toString(),"speed");
                Question[] questTab = new Question[5];
                for (int i=0;i<5;i+=1){
                    questTab[i] = DatabaseHelper.INSTANCE.questDao().randomQuestion("speed");
                }
        }
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
