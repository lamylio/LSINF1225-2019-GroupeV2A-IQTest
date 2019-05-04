package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.game;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

public class ModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeinfos);

        updateUI();
    }

    private void updateUI(){
        String s_id = getIntent().getStringExtra("mode");
        if(s_id == null) {
            Utils.changeActivity(getApplicationContext(), ChooseModeActivity.class);
            Utils.gimmeToast(getApplicationContext(), "Erreur - Impossible de retrouver le mode choisi */");
            finish();
            return;
        }
        int id = Integer.parseInt(s_id);
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
            default:
                Utils.sendLog(this.getClass(), "Unknown mode : " + id);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Utils.changeActivity(getApplicationContext(), ChooseModeActivity.class);
        finish();
    }
}
