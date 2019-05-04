package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.game;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.ProfileActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemode);
    }

    public void onModeClick(View view){
        /* We only use one eventHandler to handle all the button.
           Send to the ModeActivity and add an extraFlag with the ID of the clicked one */
        setContentView(R.layout.activity_modeinfos);
        updateUI(view);
    }

    private void updateUI(View view){
        String s_id = String.valueOf(view.getId());
        if(s_id == null) {
            Utils.changeActivity(getApplicationContext(), GameActivity.class);
            Utils.gimmeToast(getApplicationContext(), "Erreur - Impossible de retrouver le mode choisi ");
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
