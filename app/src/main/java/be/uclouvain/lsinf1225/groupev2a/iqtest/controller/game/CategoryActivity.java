package be.uclouvain.lsinf1225.groupev2a.iqtest.controller.game;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupev2a.iqtest.R;
import be.uclouvain.lsinf1225.groupev2a.iqtest.Utils;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }

    @Override
    public void onBackPressed() {
        Utils.changeActivity(getApplicationContext(), ChooseModeActivity.class);
        finish();
    }


}


