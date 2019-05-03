package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.game.ChooseModeActivity;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.user.HistoryActivity;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.user.SettingsActivity;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

public class ProfileActivity extends AppCompatActivity {

    boolean pressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_profile);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(User.loggedUser == null){
                    Utils.changeActivity(getApplicationContext(), MainActivity.class);
                    Utils.gimmeToast(getApplicationContext(), getText(R.string.NOT_LOGGED).toString());
                    Utils.sendLog(this.getClass(), "ERROR : user not logged");
                    finish();
                    return;
                }
                TextView text = findViewById(R.id.profile_username);

                Utils.sendLog(this.getClass(), "Retrieved logged user : " + User.loggedUser.getUsername());
                text.setText(User.loggedUser.getUsername());
            }
        });
    }

    public void onClickPlayButton(View view){
        Utils.changeActivity(getApplicationContext(), ChooseModeActivity.class);
        finish();
    }
    public void onClickHistoryButton(View view){
        Utils.changeActivity(getApplicationContext(), HistoryActivity.class);
        finish();
    }
    public void onClickSettingsButton(View view){
        Utils.changeActivity(getApplicationContext(), SettingsActivity.class);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        pressed = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onBackPressed() {
        /* Ask for confirmation, in case of missclick */
        if(!pressed){
            Utils.gimmeToast(getApplicationContext(), getText(R.string.SURE_DISCONNECT).toString());
            pressed = true;
        }else{
            /* Disconnect the user and get back to MainActivity */
            User.loggedUser = null;
            Utils.changeActivity(getApplicationContext(), MainActivity.class);
            finish();
        }
        super.onBackPressed();
    }
}
