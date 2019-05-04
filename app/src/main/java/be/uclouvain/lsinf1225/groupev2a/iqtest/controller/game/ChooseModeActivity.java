package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.game;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.ProfileActivity;

public class ChooseModeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemode);
    }

    public void onModeClick(View view){
        /* We only use one eventHandler to handle all the button.
           Send to the ModeActivity and add an extraFlag with the ID of the clicked one
         */
        Utils.changeActivity(getApplicationContext(), ModeActivity.class, "mode", String.valueOf(view.getId()));
        finish();
    }

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
