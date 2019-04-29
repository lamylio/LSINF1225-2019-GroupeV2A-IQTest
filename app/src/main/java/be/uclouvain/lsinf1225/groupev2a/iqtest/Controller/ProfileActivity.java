package be.uclouvain.lsinf1225.groupev2a.iqtest.Controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.Database.AppDatabase;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Database.Table.User;
import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

public class ProfileActivity extends AppCompatActivity {

    User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                TextView text = findViewById(R.id.profile_username);
                loggedUser = AppDatabase.INSTANCE.userDao().findByName(getIntent().getStringExtra("username"));

                if(loggedUser == null){
                    Utils.changeActivity(getApplicationContext(), MainActivity.class);
                    finish();
                }

                Utils.sendLog(this.getClass(), "Logged user : " + loggedUser.getUsername());
                text.setText(loggedUser.getUsername());
            }
        });
    }

    public void onClickPlayButton(View view){

        Utils.changeActivity(getApplicationContext(), ChooseModeActivity.class);
        finish();
    }

    public void onClickBackButton(View view){

        Utils.changeActivity(getApplicationContext(), ProfileActivity.class);
        finish();
    }

}
