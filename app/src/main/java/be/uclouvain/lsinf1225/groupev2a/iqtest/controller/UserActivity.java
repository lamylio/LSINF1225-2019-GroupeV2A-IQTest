package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.user.HistoryActivity;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.user.SettingsActivity;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.DatabaseHelper;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Friend;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Game;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.Result;
import be.uclouvain.lsinf1225.groupev2a.iqtest.database.room.table.User;

public class UserActivity extends AppCompatActivity {

    boolean pressed = false;
    protected static Result[] unfinished_results = null;

    TextView text_username;
    TextView text_remaining;
    Button button_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_profile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(User.loggedUser == null){
            Utils.changeActivity(getApplicationContext(), MainActivity.class);
            Utils.gimmeToast(getApplicationContext(), getText(R.string.NOT_LOGGED).toString());
            Utils.sendLog(this.getClass(), "ERROR : user not logged");
            finish();
            return;
        }
        updateUI();
    }

    private void updateUI(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.sendLog(this.getClass(), "Retrieved logged user : " + User.loggedUser.getUsername());

                /* UI Elements which need to be updated */
                text_username = findViewById(R.id.profile_username);
                text_remaining = findViewById(R.id.profile_remaining);
                button_play = findViewById(R.id.profile_buttonPlay);

                /* Database interactions */
                final Game[] games = DatabaseHelper.INSTANCE.gameDao().findByPlayer(User.loggedUser.getUsername());
                if(games != null && games.length > 0) unfinished_results = DatabaseHelper.INSTANCE.resultDao().getUnrespondedResultsFromGame(games[games.length-1].getGame_id());

                /* Update the UI with retrieved data */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text_username.setText(User.loggedUser.getUsername());
                        text_remaining.setText(games.length + (games.length > 1 ? " parties" : " partie"));

                        if(unfinished_results != null && unfinished_results.length > 0){
                            button_play.setText(getText(R.string.profile_continue));
                            text_remaining.setText("Il vous reste " + unfinished_results.length + (unfinished_results.length > 1 ? " questions" : " question"));
                        }
                    }
                });



            }
        });
        t.start();
    }

    public void onClickPlayButton(View view){
        Utils.changeActivity(getApplicationContext(), GameActivity.class);
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
    public void onClickAddFriendButton(View view){
        setContentView(R.layout.activity_addfriend);
    }

    protected void addNewFriend(View view){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               TextView friendName = findViewById(R.id.friend_username);
               User target = DatabaseHelper.INSTANCE.userDao().findByName(friendName.getText().toString());
               if(target == null) {
                   Utils.gimmeToast(getApplicationContext(), getText(R.string.INEXISTANT_USER).toString());
                   return;
               }
               else if (target.getUsername().equals(User.loggedUser.getUsername())){
                    Utils.gimmeToast(getApplicationContext(), getText(R.string.MYSELF_INVITED).toString());
                    return;
                }
               Friend check = DatabaseHelper.INSTANCE.friendDao().findByUsername(User.loggedUser.getUsername());
               if (check != null){
                   if (check.getStatus() == 0){
                       Utils.gimmeToast(getApplicationContext(), getText(R.string.ALREADY_INVITED).toString());
                       return;
                   }else{
                       Utils.gimmeToast(getApplicationContext(), getText(R.string.ALREADY_FRIEND).toString().replace("%username%", target.getUsername()));
                       return;
                   }
               }else{
                   Friend friendship = new Friend(User.loggedUser.getUsername(), friendName.getText().toString());
                   DatabaseHelper.INSTANCE.friendDao().insertFriendship(friendship);
                   Utils.gimmeToast(getApplicationContext(), getText(R.string.FRIEND_INVITED).toString());
                   Utils.changeActivity(getApplicationContext(), UserActivity.class);
               }
            }
        });
        t.start();
        try{
            t.join();
        }catch (InterruptedException e){
            Log.e("IQW/UserActivity", e.getMessage());
        }

    }

    /* ----- ----- */

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
        if(getCurrentFocus() != findViewById(R.id.activity_addfriend)){
            setContentView(R.layout.activity_profile);
            updateUI();
        }else {
            if(!pressed){
                Utils.gimmeToast(getApplicationContext(), getText(R.string.SURE_DISCONNECT).toString());
                pressed = true;
            }else{
                /* Disconnect the user and get back to MainActivity */
                disconnect(getApplicationContext());
                finish();
            }
        }
    }

    public static void disconnect(Context appcontext){
        User.loggedUser = null;
        GameActivity.game = null;
        GameActivity.answersTable = null;
        Utils.changeActivity(appcontext, MainActivity.class);
    }
}
