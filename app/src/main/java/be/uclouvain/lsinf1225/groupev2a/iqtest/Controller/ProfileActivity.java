package be.uclouvain.lsinf1225.groupev2a.iqtest.Controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.Controller.Game.ChooseModeActivity;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Database.Table.User;
import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

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
    }
    public void onClickHistoryButton(View view){
        Utils.changeActivity(getApplicationContext(), HistoryActivity.class);
    }
    public void onClickParametersButton(View view){
        Utils.changeActivity(getApplicationContext(), ParametersActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pressed = false;
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
    }
}
