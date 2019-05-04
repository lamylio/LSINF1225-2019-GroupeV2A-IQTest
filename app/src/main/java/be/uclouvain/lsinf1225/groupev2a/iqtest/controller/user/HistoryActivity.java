package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;
import be.uclouvain.lsinf1225.groupev2a.iqtest.controller.ProfileActivity;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    @Override
    public void onBackPressed() {
        Utils.changeActivity(getApplicationContext(), ProfileActivity.class);
        finish();
    }
}