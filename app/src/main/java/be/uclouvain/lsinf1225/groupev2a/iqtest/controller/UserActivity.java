package be.uclouvain.lsinf1225.groupev2a.iqtest.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
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
        /* Nous vérifions si l'utilisateur est connecté */
        if(User.loggedUser == null){
            Utils.changeActivity(getApplicationContext(), MainActivity.class);
            Utils.gimmeToast(getApplicationContext(), getText(R.string.NOT_LOGGED).toString());
            Utils.sendLog(this.getClass(), "ERROR : user not logged");
            finish();
            return;
        }
        updateUI();
    }

    private Activity getActivity(){return this;}

    /* */
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
                /* Dans le cas où l'utilisateur a encore un quizz non terminé */
                if(games != null && games.length > 0) unfinished_results = DatabaseHelper.INSTANCE.resultDao().getUnrespondedResultsFromGame(games[games.length-1].getGame_id());

                /* Update the UI with retrieved data */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text_username.setText(User.loggedUser.getUsername());
                        text_remaining.setText(games.length + (games.length > 1 ? " parties" : " partie"));

                        if(unfinished_results != null && unfinished_results.length > 0){
                            text_remaining.setText("Il vous reste " + unfinished_results.length + (unfinished_results.length > 1 ? " questions" : " question"));
                            button_play.setText(getText(R.string.profile_continue));
                        }
                    }
                });
            }
        });
        t.start();
    }

    /* ----- ----- */

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

    /* ----- FRIENDS ------ */

    public void onClickMyFriendButton(View view){
        setContentView(R.layout.activity_myfriends);
        updateFriendUI();
    }

    public void onClickAddFriend(View view){
        setContentView(R.layout.activity_addfriend);
    }

    Friend[] friends;
    TableLayout table;
    TableRow[] rows;
    private void updateFriendUI(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                table = findViewById(R.id.myfriends_table);
                friends = DatabaseHelper.INSTANCE.friendDao().findByUsername(User.loggedUser.getUsername());
                rows = new TableRow[friends.length];
                for(int i = 0; i < friends.length; i++){
                    TableRow row = new TableRow(getActivity());
                    row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    row.setPadding(0, 10, 0, 10);
                    rows[i] = row;

                    TextView[] columns = new TextView[2];
                    for (int j = 0; j < columns.length; j++) {
                        TextView text = new TextView(getActivity());
                        text.setTextColor(getResources().getColor(R.color.white));
                        text.setTextSize(18);
                        text.setGravity(Gravity.CENTER);
                        columns[j] = text;
                        row.addView(text);
                    }
                    String status;
                    if(friends[i].getStatus() == 0){
                        status = getResources().getText(R.string.WAITING).toString();
                    }else {
                        status = getResources().getText(R.string.FRIENDS).toString();
                    }

                    columns[0].setText(friends[i].getUser2());
                    columns[1].setText(status);
                    // TODO: columns[2].setText(getResources().getText(R.string.FIGHT).toString());
                }

            }
        });
        thread.start();
        /* Attente que le thread soit terminé */
        try{
            thread.join();
        }catch (InterruptedException e){
            Log.e("IQW/UserActivity", e.getMessage());
        }

        /* Afficher chacune des lignes */
        if(friends != null && friends.length > 0) table.removeAllViews();
        for(TableRow row : rows){
            table.addView(row);
        }
    }

    protected void addNewFriend(View view){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                TextView friendName = findViewById(R.id.friend_username);
                User target = DatabaseHelper.INSTANCE.userDao().findByName(friendName.getText().toString());
                /* On vérifie que le joueur cible existe */
                if (target != null) {
                    Friend check = DatabaseHelper.INSTANCE.friendDao().areTheyFriends(User.loggedUser.getUsername(), target.getUsername());
                    if (check == null) {
                        /* Si l'amitié n'existe pas on l'ajoute */
                        DatabaseHelper.INSTANCE.friendDao().insertFriendship(new Friend(User.loggedUser.getUsername(), friendName.getText().toString()));
                        DatabaseHelper.INSTANCE.friendDao().insertFriendship(new Friend(friendName.getText().toString(), User.loggedUser.getUsername()));
                        Utils.gimmeToast(getApplicationContext(), getText(R.string.FRIEND_INVITED).toString());
                        Utils.changeActivity(getApplicationContext(), UserActivity.class);
                    } else {
                        /* Si l'amitié existe, on vérifie le status pour afficher un message en conséquence */
                        if (check.getStatus() == 0) {
                            Utils.gimmeToast(getApplicationContext(), getText(R.string.ALREADY_INVITED).toString());
                        } else {
                            Utils.gimmeToast(getApplicationContext(), getText(R.string.ALREADY_FRIEND).toString().replace("%username%", target.getUsername()));
                        }
                    }
                }else {
                    Utils.gimmeToast(getApplicationContext(), getText(R.string.INEXISTANT_USER).toString());
                }
            }
        });
        t.start();
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
        if(getCurrentFocus() != findViewById(R.id.activity_myfriends)){
            setContentView(R.layout.activity_profile);
            updateUI();
        }else if (getCurrentFocus() != findViewById(R.id.activity_addfriend)){
            setContentView(R.layout.activity_myfriends);
        }else{
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
