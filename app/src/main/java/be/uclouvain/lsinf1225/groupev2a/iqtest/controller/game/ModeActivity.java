package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.game;

import android.os.Bundle;

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
        /* Debug en cours */
        switch (id) {
            case R.id.choose_friendButton:
                Utils.sendLog(this.getClass(), "Friend");
                break;
            case R.id.choose_normalButton:
                Utils.sendLog(this.getClass(), "Normal");
                break;
            case R.id.choose_fastButton:
                Utils.sendLog(this.getClass(), "Fast");
                break;
            case R.id.choose_categoriesButton:
                Utils.sendLog(this.getClass(), "Categories");
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
